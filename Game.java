import java.util.Random;
/**
 *  Dies ist die Hauptklasse der Anwendung "MiniAdventure".
 *  "MiniAdventure" ist ein sehr einfaches, textbasiertes
 *  Adventure-Game. Ein Spieler kann sich in einer Umgebung bewegen,
 *  mehr nicht. Das Spiel sollte auf jeden Fall ausgebaut werden,
 *  damit es interessanter wird!
 * 
 *  Zum Spielen muss eine Instanz dieser Klasse erzeugt werden und
 *  an ihr die Methode "spielen" aufgerufen werden.
 * 
 *  Diese Instanz dieser Klasse erzeugt und initialisiert alle
 *  anderen Objekte der Anwendung: Sie legt alle R�ume und einen
 *  Parser an und startet das Spiel. Sie wertet auch die Befehle
 *  aus, die der Parser liefert und sorgt f�r ihre Ausf�hrung.
 * 
 * @author  auf Grundlage von Michael K�lling und David J. Barnes
 * 
 */

class Game 
{
    private Parser parser;
    private Room currentRoom;
    private Player player;
    private Random rand;
    private boolean containerIsOpened;
    private boolean isFighting = false;
    /**
     * Erzeuge ein Spiel und initialisiere die interne Raumkarte.
     */
    public Game() 
    {   
        raeumeAnlegen();
        parser = new Parser();
        player = new Player();
        rand = new Random();
    }

    /**
     * Erzeuge alle R�ume und verbinde ihre Ausg�nge miteinander.
     */
    private void raeumeAnlegen()
    {
        Room garten, flur, wohnzimmer, schlafzimmer, k�che, speisekammer, rutsche, gew�chshaus, treppenhaus, waffenkammer, thronsaal, weinkeller;

        // die R�ume erzeugen
        garten = new Room("im Garten vor einem kleinen Haus");
        flur = new Room("in einem engen Flur");
        wohnzimmer = new Room("in einem Wohnzimmer, das nur von einer Kerze beleuchtet wird");
        schlafzimmer = new Room("in einem Schlafzimmer mit morschem Bett und Wanzen");
        k�che = new Room("in einer K�che mit einem Tisch, auf dem alte verkrustete T�pfe stehen");
        speisekammer = new Room("in einer kleinen Speisekammer die voller Schr�nke mit verstaubten Gl�sern ist");
        rutsche = new Room("durch eine Rutsche mit einer dunklen R�hre gerutscht", true);
        gew�chshaus = new Room("im Gew�chshaus mit vielen gro�en Pflanzen, welche sch�ne gro�e Fr�chte tragen");
        treppenhaus = new Room("im Treppenhaus, dessen Stufen schon sehr morsch und gef�hrlich aus sehen");
        waffenkammer = new Room("in einer Waffenkammer mit Waffenschr�nken, in welchen viele alte Waffen liegen, genauso wie alte R�stungen");
        thronsaal = new Room("in einem impossanten Thronsaal, in dem es einen langen Reihe aus Statuen gibt, die zu einem riesigen Thron f�hrt");
        weinkeller = new Room("im Weinkeller, an dessen W�nden gro�e Regale mit sehr vielen Weinflaschen stehen");

        // die Ausg�nge initialisieren
        garten.setExit("north", flur);
        flur.setExit("east", k�che);
        flur.setExit("west", wohnzimmer);
        flur.setExit("south", garten);
        wohnzimmer.setExit("east", flur);
        wohnzimmer.setExit("south", schlafzimmer);
        wohnzimmer.setExit("north", rutsche);
        schlafzimmer.setExit("north", wohnzimmer);
        k�che.setExit("west", flur);
        k�che.setExit("east", speisekammer);
        speisekammer.setExit("west", k�che);
        speisekammer.setExit("south", treppenhaus);
        gew�chshaus.setExit("west", garten);
        treppenhaus.setExit("south", weinkeller);
        weinkeller.setExit("east", waffenkammer);
        waffenkammer.setExit("north", thronsaal);
        waffenkammer.setExit("west", weinkeller);
        rutsche.setExit("east", waffenkammer);

        // Gegenst�nde verteilen
        k�che.addWeapon("Messer", "ein gro�es scharfes K�chenmesser", 10);
        k�che.addWeapon("Keule", "eine gro�e t�dliche Keule", 10);
        k�che.addFood("Brot", "trotz das dieses Haus sehr herunter gekommern und verlassen scheint, sieht diese Brot sehr Frisch aus", 20);
        schlafzimmer.addKey("Schl�ssel", "ein gro�er Schl�ssel", 2);
        garten.addFood("Apfel", "ein sch�ner, gl�nzend roter Apfel", 15);

        // Container erschaffen
        schlafzimmer.addContainer("Kleiderschrank", 2);

        //Gegner erschaffen
        speisekammer.addEnemy("Rattenk�nig", //name
            "eine riesige Ratte, die so gro� ist wie ein Hund", //description
            new Weapon("Fleischhammer", "ein gro�er blutverschmierter Fleischhammer", 5), //weapon
            60, //agility
            30, //health
            "Keks", //food
            new Food("Apfel", "ein goldener Apfel", 30)); //drop

        currentRoom = garten;  // das Spiel startet in Raum garten
    }

    /**
     * Die Hauptmethode zum Spielen. L�uft bis zum Ende des Spiels
     * in einer Schleife.
     */
    public void spielen() 
    {            
        willkommenstextAusgeben();

        // Die Hauptschleife. Hier lesen wir wiederholt Befehle ein
        // und f�hren sie aus, bis das Spiel beendet wird.

        boolean beendet = false;
        while (! beendet) {
            Befehl command = parser.liefereBefehl();
            beendet = verarbeiteBefehl(command);
        }
        System.out.println("Danke f�rs Spielen. Auf Wiedersehen.");
    }

    /**
     * Einen Begr��ungstext f�r den Spieler ausgeben.
     */
    private void willkommenstextAusgeben()
    {
        System.out.println();
        System.out.println("Willkommen zu MiniAdventure!");
        System.out.println("MiniAdventure ist ein viel zu kleines Spiel.");
        System.out.println("Tippen Sie 'help', wenn Sie Hilfe brauchen.");
        System.out.println();
        System.out.println(currentRoom.getLongDescription());
    }

    /**
     * Verarbeite einen gegebenen Befehl (f�hre ihn aus).
     * @param command  der zu verarbeitende Befehl.
     * @return        true, wenn der Befehl das Spiel beendet, 
     *                false sonst
     */
    private boolean verarbeiteBefehl(Befehl command) 
    {
        boolean moechteBeenden = false;
        String commandWord = command.gibBefehlswort();
        if(command.istUnbekannt()) {
            System.out.println("Ich weiss nicht, was Sie meinen ...");
            return false;    
        }    

        if (commandWord.equals("quit") || commandWord.equals("exit") || commandWord.equals("stop")) {
            return moechteBeenden = exit(command);
        }

        if(parser.getCommands().isContainerCommand(commandWord) && !containerIsOpened)    
        {    
            System.out.println("Sie haben keinen Container ge�ffnet");
            return false;
        }

        if(containerIsOpened && commandWord.equals("close"))
        {
            containerIsOpened = false;
            System.out.println("Du hast " + currentRoom.getContainer().getName() + " geschlossen.");
        }
        else if(containerIsOpened && command.hasSecondCommand())
        {
            handleContainerCommand(command);
            return false;
        }

        if(commandWord.equals("open") && !containerIsOpened)
        {
            if(!currentRoom.hasContainer())
            {
                System.out.println("Dieser Raum besitzt keinen Container...");
                return false;
            }
            else if(command.hasSecondCommand() && command.gibZweitesWort().equals(currentRoom.getContainer().getName()))
            {
                currentRoom.container.printContents();
                System.out.println("M�gliche Befehle: close, put, take");
                containerIsOpened = true;
                return false;
            }
            else if(command.hasSecondCommand())
            {
                System.out.println("Dieser Raum hat kein Beh�ltnis mit " + command.gibZweitesWort());
                return false;
            }
        }

        if(commandWord.equals("attack") && isFighting){
            if(currentRoom.damageEnemy(attack(player.returnWeapon())) == false)
            {
                player.changeHealth(currentRoom.enemyAttack(player.getAgility()));
            }
            else
            {
                isFighting = false;
            }
            return false;
        }
        else if(commandWord.equals("attack")){
            System.out.println("Es ist kein Gegner mit dir im Raum den du angreifen k�nntest.");
        }

        if (commandWord.equals("feed") && isFighting){
            if(command.hasSecondCommand() && player.hasItem(command.gibZweitesWort()))
            {
                if(!currentRoom.feedEnemy(command.gibZweitesWort())) //aktueller Raum muss einen Gegner haben, da der Spieler bereits im Kampf ist
                {
                    System.out.println("Dem Gegner hat diese Nahrung nicht geschmeckt.");
                }
                player.removeItem(command.gibZweitesWort());
            }
            else if(command.hasSecondCommand())
            {
                System.out.println("Dieses Essen liegt nicht in ihrem Inventar.");
            }
            else
            {
                System.out.println("Geben Sie eine Nahrung zum F�ttern an.");
            }
            return false;
        }

        
        if (commandWord.equals("escape") && isFighting){
            if(command.hasSecondCommand()){
                int result = currentRoom.tryingToEscape(command.gibZweitesWort());
                if(result == 0){
                    System.out.println("Du hast es geschafft zu fl�chten.");
                    changeRoom(command);
                    checkForEnemy();
                    isFighting = false;
                }
                else if(result == 1){
                    System.out.println("Du hast es nicht geschafft weg zu laufen, der Gegner hat dich daran gehindert.");
                    player.changeHealth(currentRoom.enemyAttack(player.getAgility()));
                }
                else if(result == 2){
                    System.out.println("Auf dieser Seite des Raumes gibt es keine T�r.");
                }
            }
            else{
                System.out.println("Wohin soll versucht werden zu fl�hten");
            }
            return false;
        }

        if (commandWord.equals("enemy")){
            currentRoom.printEnemyDescription();
            return false;
        }

        if(containerIsOpened)
        {
            System.out.println("Du musst " + currentRoom.container.getName() + " erst schlie�en bevor du etwas anderes tun kannst.");
            return false;
        }

        if(isFighting){
            if(!currentRoom.hasEnemy())
            {
                isFighting = false;
            }
            else
            {
                System.out.println("Du kannst erst nach dem Kampf wieder was anderes machen.");
                return false;
            }
        }

        if (commandWord.equals("help")) {
            hilfstextAusgeben();
        }
        else if (commandWord.equals("go")) {
            if(player.getSaturation() <= 0){
                System.out.println("Du verhungerst");
                player.changeHealth(-10);
            }
            else{
                player.loseSaturation();
            }
            changeRoom(command);
        }
        else if (commandWord.equals("look")){
            if(command.hasSecondCommand()){
                lookForItem(command.gibZweitesWort()); 
            }
            else{
                lookForItemsInRoom();  
            }
        }
        else if (commandWord.equals("inventory")){
            player.printInventory();
        }
        else if (commandWord.equals("stats")){
            player.printStats();
        }
        else if (commandWord.equals("eat")){
            if(command.hasSecondCommand()){
                player.eat(command.gibZweitesWort());
            }
            else{
                System.out.println("Es wurde keine Essen bestimmt welches gegessen werden soll");
            }
        }
        else if (commandWord.equals("pickup")){
            if(command.hasSecondCommand()){
                player.pickUp(currentRoom.findItem(command.gibZweitesWort()), currentRoom);
            }
            else{
                System.out.println("Was soll auf gehoben werden?");
            }
        }
        else if (commandWord.equals("feed"))
        {
            System.out.println("Du bist nicht im Kampf.");
        }
        else if (commandWord.equals("heal"))
        {
            player.heal();  
        }
        // ansonsten: Befehl nicht erkannt.
        return moechteBeenden;
    }

    private void handleContainerCommand(Befehl command)
    {
        if(command.gibBefehlswort().equals("put"))
        {
            if(!player.putItemInContainer(currentRoom.getContainer(), command.gibZweitesWort()))
            {
                System.out.println("Dieser Gegenstand liegt nicht in deinem Inventar!");
            }
        }
        else if(command.gibBefehlswort().equals("take"))
        {
            if(!player.pickUp(currentRoom.getContainer().takeItem(command.gibZweitesWort(), player.hasWeapon()), currentRoom))
            {
                System.out.println("Dieser Gegenstand liegt nicht in " + currentRoom.getContainer().getName());
            }
        }
        else
        {
            System.out.println("Du musst " + currentRoom.getContainer().getName() + " erst schlie�en bevor du etwas anderes tun kannst.");
        }
    }

    // Implementierung der Benutzercommande:

    /**
     * Gib Hilfsinformationen aus.
     * Hier geben wir eine etwas alberne und unklare Beschreibung
     * aus, sowie eine Liste der Befehlsw�rter.
     */
    private void hilfstextAusgeben() 
    {
        System.out.println("Sie wissen nicht was zu tun ist, Sie sind allein?");
        System.out.println();
        System.out.println("Ihnen stehen folgende Befehle zur Verf�gung:");
        parser.zeigeBefehle();
    }

    /**
     * Versuche, in eine Richtung zu gehen. Wenn es einen Ausgang gibt,
     * wechsele in den neuen Raum, ansonsten gib eine Fehlermeldung
     * aus.
     */
    private void changeRoom(Befehl command) 
    {
        if(!command.hasSecondCommand()) {
            // Gibt es kein zweites Wort, wissen wir nicht, wohin...
            System.out.println("Geben Sie eine Richtung an! (z.B. go north)");
            return;
        }

        // Wir versuchen, den Raum zu verlassen.
        Room nextRoom = currentRoom.getExit(command.gibZweitesWort());

        if (nextRoom == null) {
            System.out.println("Dort ist keine T�r!");
        }
        else if(nextRoom.checkIfLocked() && !player.hasKey())
        {
            System.out.println("Diese T�r ben�tigt einen Schl�ssel um ge�ffnet zu werden!");
        }
        else {
            currentRoom = nextRoom;
            System.out.println(currentRoom.getLongDescription());

            checkForEnemy();
        }
    }

    /**
     * "quit" wurde eingegeben. �berpr�fe den Rest des Befehls,
     * ob das Spiel wirklich beendet werden soll.
     * @return  true, wenn der Befehl das Spiel beendet, 
     *          false sonst
     */
    private boolean exit(Befehl command) 
    {
        if(command.hasSecondCommand()) {
            System.out.println("Was soll beendet werden?");
            return false;
        }
        else {
            return true;  // Das Spiel soll beendet werden.
        }
    }

    private void lookForItemsInRoom (){
        currentRoom.printAlleGegenstaende();
    }

    private void lookForItem(String name){
        System.out.println(currentRoom.getItemDescription(name)); 
    }

    private void checkForEnemy(){
        if(currentRoom.hasEnemy()){
            System.out.println("Es befindet sich ein Gegner mit dir im Raum.");
            System.out.println("Zur Verf�gung stehende Befehle: attack, escape, feed");
            isFighting = true;
        }
    }

    private int attack(Weapon weapon){
        if(weapon == null){
            System.out.println("Du hast keine Waffe mit der du angreifen k�nntest.");
            return -1;
        }
        if(rand.nextInt(100) > currentRoom.getEnemy().getAgility()){
            return weapon.getStat();
        }
        System.out.println("Du hast den Gegner verfehlt");
        return -1;
    }

    public static void main(String args[]){
        Game myGame = new Game();
        myGame.spielen();
    }
}
