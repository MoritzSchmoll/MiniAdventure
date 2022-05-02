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
 *  anderen Objekte der Anwendung: Sie legt alle Räume und einen
 *  Parser an und startet das Spiel. Sie wertet auch die Befehle
 *  aus, die der Parser liefert und sorgt für ihre Ausführung.
 * 
 * @author  auf Grundlage von Michael Kölling und David J. Barnes
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
     * Erzeuge alle Räume und verbinde ihre Ausgänge miteinander.
     */
    private void raeumeAnlegen()
    {
        Room garten, flur, wohnzimmer, schlafzimmer, küche, speisekammer, rutsche, gewächshaus, treppenhaus, waffenkammer, thronsaal, weinkeller;

        // die Räume erzeugen
        garten = new Room("im Garten vor einem kleinen Haus");
        flur = new Room("in einem engen Flur");
        wohnzimmer = new Room("in einem Wohnzimmer, das nur von einer Kerze beleuchtet wird");
        schlafzimmer = new Room("in einem Schlafzimmer mit morschem Bett und Wanzen");
        küche = new Room("in einer Küche mit einem Tisch, auf dem alte verkrustete Töpfe stehen");
        speisekammer = new Room("in einer kleinen Speisekammer die voller Schränke mit verstaubten Gläsern ist");
        rutsche = new Room("durch eine Rutsche mit einer dunklen Röhre gerutscht", true);
        gewächshaus = new Room("im Gewächshaus mit vielen großen Pflanzen, welche schöne große Früchte tragen");
        treppenhaus = new Room("im Treppenhaus, dessen Stufen schon sehr morsch und gefährlich aus sehen");
        waffenkammer = new Room("in einer Waffenkammer mit Waffenschränken, in welchen viele alte Waffen liegen, genauso wie alte Rüstungen");
        thronsaal = new Room("in einem impossanten Thronsaal, in dem es einen langen Reihe aus Statuen gibt, die zu einem riesigen Thron führt");
        weinkeller = new Room("im Weinkeller, an dessen Wänden große Regale mit sehr vielen Weinflaschen stehen");

        // die Ausgänge initialisieren
        garten.setExit("north", flur);
        flur.setExit("east", küche);
        flur.setExit("west", wohnzimmer);
        flur.setExit("south", garten);
        wohnzimmer.setExit("east", flur);
        wohnzimmer.setExit("south", schlafzimmer);
        wohnzimmer.setExit("north", rutsche);
        schlafzimmer.setExit("north", wohnzimmer);
        küche.setExit("west", flur);
        küche.setExit("east", speisekammer);
        speisekammer.setExit("west", küche);
        speisekammer.setExit("south", treppenhaus);
        gewächshaus.setExit("west", garten);
        treppenhaus.setExit("south", weinkeller);
        weinkeller.setExit("east", waffenkammer);
        waffenkammer.setExit("north", thronsaal);
        waffenkammer.setExit("west", weinkeller);
        rutsche.setExit("east", waffenkammer);

        // Gegenstände verteilen
        küche.addWeapon("Messer", "ein großes scharfes Küchenmesser", 10);
        küche.addWeapon("Keule", "eine große tödliche Keule", 10);
        küche.addFood("Brot", "trotz das dieses Haus sehr herunter gekommern und verlassen scheint, sieht diese Brot sehr Frisch aus", 20);
        schlafzimmer.addKey("Schlüssel", "ein großer Schlüssel", 2);
        garten.addFood("Apfel", "ein schöner, glänzend roter Apfel", 15);

        // Container erschaffen
        schlafzimmer.addContainer("Kleiderschrank", 2);

        //Gegner erschaffen
        speisekammer.addEnemy("Rattenkönig", //name
            "eine riesige Ratte, die so groß ist wie ein Hund", //description
            new Weapon("Fleischhammer", "ein großer blutverschmierter Fleischhammer", 5), //weapon
            60, //agility
            30, //health
            "Keks", //food
            new Food("Apfel", "ein goldener Apfel", 30)); //drop

        currentRoom = garten;  // das Spiel startet in Raum garten
    }

    /**
     * Die Hauptmethode zum Spielen. Läuft bis zum Ende des Spiels
     * in einer Schleife.
     */
    public void spielen() 
    {            
        willkommenstextAusgeben();

        // Die Hauptschleife. Hier lesen wir wiederholt Befehle ein
        // und führen sie aus, bis das Spiel beendet wird.

        boolean beendet = false;
        while (! beendet) {
            Befehl command = parser.liefereBefehl();
            beendet = verarbeiteBefehl(command);
        }
        System.out.println("Danke fürs Spielen. Auf Wiedersehen.");
    }

    /**
     * Einen Begrüßungstext für den Spieler ausgeben.
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
     * Verarbeite einen gegebenen Befehl (führe ihn aus).
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
            System.out.println("Sie haben keinen Container geöffnet");
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
                System.out.println("Mögliche Befehle: close, put, take");
                containerIsOpened = true;
                return false;
            }
            else if(command.hasSecondCommand())
            {
                System.out.println("Dieser Raum hat kein Behältnis mit " + command.gibZweitesWort());
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
            System.out.println("Es ist kein Gegner mit dir im Raum den du angreifen könntest.");
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
                System.out.println("Geben Sie eine Nahrung zum Füttern an.");
            }
            return false;
        }

        
        if (commandWord.equals("escape") && isFighting){
            if(command.hasSecondCommand()){
                int result = currentRoom.tryingToEscape(command.gibZweitesWort());
                if(result == 0){
                    System.out.println("Du hast es geschafft zu flüchten.");
                    changeRoom(command);
                    checkForEnemy();
                    isFighting = false;
                }
                else if(result == 1){
                    System.out.println("Du hast es nicht geschafft weg zu laufen, der Gegner hat dich daran gehindert.");
                    player.changeHealth(currentRoom.enemyAttack(player.getAgility()));
                }
                else if(result == 2){
                    System.out.println("Auf dieser Seite des Raumes gibt es keine Tür.");
                }
            }
            else{
                System.out.println("Wohin soll versucht werden zu flühten");
            }
            return false;
        }

        if (commandWord.equals("enemy")){
            currentRoom.printEnemyDescription();
            return false;
        }

        if(containerIsOpened)
        {
            System.out.println("Du musst " + currentRoom.container.getName() + " erst schließen bevor du etwas anderes tun kannst.");
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
            System.out.println("Du musst " + currentRoom.getContainer().getName() + " erst schließen bevor du etwas anderes tun kannst.");
        }
    }

    // Implementierung der Benutzercommande:

    /**
     * Gib Hilfsinformationen aus.
     * Hier geben wir eine etwas alberne und unklare Beschreibung
     * aus, sowie eine Liste der Befehlswörter.
     */
    private void hilfstextAusgeben() 
    {
        System.out.println("Sie wissen nicht was zu tun ist, Sie sind allein?");
        System.out.println();
        System.out.println("Ihnen stehen folgende Befehle zur Verfügung:");
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
            System.out.println("Dort ist keine Tür!");
        }
        else if(nextRoom.checkIfLocked() && !player.hasKey())
        {
            System.out.println("Diese Tür benötigt einen Schlüssel um geöffnet zu werden!");
        }
        else {
            currentRoom = nextRoom;
            System.out.println(currentRoom.getLongDescription());

            checkForEnemy();
        }
    }

    /**
     * "quit" wurde eingegeben. Überprüfe den Rest des Befehls,
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
            System.out.println("Zur Verfügung stehende Befehle: attack, escape, feed");
            isFighting = true;
        }
    }

    private int attack(Weapon weapon){
        if(weapon == null){
            System.out.println("Du hast keine Waffe mit der du angreifen könntest.");
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
