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
 *  Parser an und startet das Spiel. Sie wertet auch die Commande
 *  aus, die der Parser liefert und sorgt für ihre Ausführung.
 * 
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
        Room garten, flur, wohnzimmer, schlafzimmer, küche, speisekammer, rutsche, gewaechshaus, treppenhaus, waffenkammer, thronsaal, weinkeller;

        // die Räume erzeugen
        garten = new Room("im Garten vor einem kleinen Haus");
        flur = new Room("in einem engen Flur");
        wohnzimmer = new Room("in einem Wohnzimmer, das nur von einer Kerze beleuchtet wird");
        schlafzimmer = new Room("in einem Schlafzimmer mit morschem Bett und Wanzen");
        küche = new Room("in einer Küche mit einem Tisch, auf dem alte verkrustete Töpfe stehen");
        speisekammer = new Room("in einer kleinen Speisekammer die voller Schränke mit verstaubten Gläsern ist");
        rutsche = new Room("durch eine Rutsche mit einer dunklen Röhre gerutscht", true);
        gewaechshaus = new Room("im Gewächshaus mit vielen großen Pflanzen, welche schöne große Früchte tragen");
        treppenhaus = new Room("im Treppenhaus, dessen Stufen schon sehr morsch und gefährlich aus sehen");
        waffenkammer = new Room("in einer Waffenkammer mit Waffenschränken, in welchen viele alte Waffen liegen, genauso wie alte Rüstungen");
        thronsaal = new Room("in einem impossanten Thronsaal, in dem es einen langen Reihe aus Statuen gibt, die zu einem riesigen Thron führt");
        weinkeller = new Room("im Weinkeller, an dessen Wänden große Regale mit sehr vielen Weinflaschen stehen");

        // die Ausgänge initialisieren
        garten.setExit("north", flur);
        garten.setExit("east", gewaechshaus);
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
        gewaechshaus.setExit("west", garten);
        treppenhaus.setExit("south", weinkeller);
        weinkeller.setExit("east", waffenkammer);
        waffenkammer.setExit("north", thronsaal);
        waffenkammer.setExit("west", weinkeller);
        rutsche.setExit("east", waffenkammer);
        
        // Gegenstände verteilen
        wohnzimmer.addItem(new Food("Chips", "eine große Tüte Chips", 10,1));
        flur.addItem(new Book("Fantasyroman", "ein dickes Buch mit dem Titel **Dieses Element musste aus Copyright rechtlichen Gründen entfernt werden**", 5 ));
        küche.addItem(new Weapon("Messer", "ein großes scharfes Küchenmesser", 6, 4));
        küche.addItem(new Weapon("Keule", "eine große tödliche Keule", 10, 6));
        küche.addItem(new Food("Brot", "trotz das dieses Haus sehr herunter gekommern und verlassen scheint, sieht diese Brot sehr Frisch aus", 20, 2));
        speisekammer.addItem(new Food("Schinkenkeule", "ein großer sehr Schmackhaft aus sehender Schinken",25,6));
        treppenhaus.addItem(new Weapon("Besen", "ein Besen, der an einen Hexenbesen errinnert",3,2));
        schlafzimmer.addItem(new Weapon("Flasche", "eine zerbrochene Bierflasche", 3, 2));
        garten.addItem(new Food("Apfel", "ein schöner, glänzend roter Apfel", 15, 2));
        gewaechshaus.addItem(new Food("Melone", "eine sehr große Melone", 10, 5));
        gewaechshaus.addItem(new Food("Karotte", "eine kleine Karotte", 5, 1));
        weinkeller.addItem(new Food("Moet", "eine große Flasche Champagner", 5, 5));
        waffenkammer.addItem(new Weapon("Schwert", "ein Schwert mit einer großen Klinge", 15, 4));
               
        // Container erschaffen
        schlafzimmer.addContainer("Kleiderschrank", 2);
        waffenkammer.addContainer("Waffenschrank", 3, true);
        garten.addContainer("Stein",1);
        
        // Gegenstände im Container erschaffen
        schlafzimmer.getContainer().addItem(new Key("Schlüssel", "ein großer mysteriöser Schlüssel", 1, "Door"));
        schlafzimmer.getContainer().addItem(new Book("Schriftrolle", "eine alte Schriftrolle in dem Text in einer hyroglyphenartigen Schrift steht", 4));
        waffenkammer.getContainer().addItem(new Weapon("Revolver", "ein alter Revolver", 20, 5));
        garten.getContainer().addItem(new Key("Schlüsselkarte", "eine moderne Schlüsselkarte", 2, "Locker"));
    
        
        //Gegner erschaffen
        speisekammer.addEnemy("Rattenkönig", //name
                "eine riesige Ratte, die so groß ist wie ein Hund", //description
                new Weapon("Fleischhammer", "ein großer blutverschmierter Fleischhammer", 5, 5), //weapon
                60, //agility
                30, //health
                "Keks", //food
                new Food("Goldkeks", "ein goldener Keks", 30, 2)); //drop
            
        thronsaal.addEnemy("Skelletkönig",
                "Der Geist von König Artus in Form eines großen angsteinflössendem Skellets",
            new Weapon("Excalibur", "das legendäre Schwert von König Artus", 30, 15),
            90,
            500,
            "Schriftrolle",
            new Weapon("Excalibur", "das legendäre Schwert von König Artus", 30, 15)); //dro

        currentRoom = garten;  // das Spiel startet in Raum garten
    }

    /**
     * Die Hauptmethode zum Spielen. Läuft bis zum Ende des Spiels
     * in einer Schleife.
     */
    public void spielen() 
    {            
        willkommenstextAusgeben();

        // Die Hauptschleife. Hier lesen wir wiederholt Commande ein
        // und führen sie aus, bis das Spiel beendet wird.

        boolean beendet = false;
        while (! beendet) {
            Command command = parser.liefereCommand();
            beendet = verarbeiteCommand(command);
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
        System.out.println("Das Ziel des Spiel ist es das legendäre Schwert Excalibur zu finden und aufzuheben.");
        System.out.println("Sei immer auf der Hut und bereite dich schnells möglich auf einen Kampf vor");
        System.out.println();
        System.out.println("Tippen Sie 'help', wenn Sie unsicher sind welche Befehle es gibt.");
        System.out.println();
        System.out.println(currentRoom.getLongDescription());
    }

    /**
     * Verarbeite einen gegebenen Command (führe ihn aus).
     * @param command  der zu verarbeitende Command.
     * @return        true, wenn der Command das Spiel beendet, 
     *                false sonst
     */
    private boolean verarbeiteCommand(Command command) 
    {
        boolean moechteBeenden = false;
        String commandWord = command.getCommand();
        if(command.istUnbekannt()) {
            System.out.println("Ich weiss nicht, was Sie meinen ...");
            return false;    
        }    

        if (commandWord.equals("quit") || commandWord.equals("exit") || commandWord.equals("stop")) {
            return moechteBeenden = exit(command);
        }

        if(parser.getCommandWords().isContainerCommand(commandWord) && !containerIsOpened)    
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
            else if(command.hasSecondCommand() && command.getSecondWord().equals(currentRoom.getContainer().getName()))
            {
                if(currentRoom.getContainer().isLocked() && !player.hasKeyOfType("Locker"))
                {
                    System.out.println("Dieser Container benötigt eine spezielle Schlüsselkarte.");
                    return false;
                }
                
                currentRoom.container.printContents();
                System.out.println("Mögliche Befehle: close, put {item}, take {item}");
                containerIsOpened = true;
                return false;
            }
            else if(command.hasSecondCommand())
            {
                System.out.println("Dieser Raum hat kein Behältnis mit dem Namen " + command.getSecondWord());
                return false;
            }
        }

        if(commandWord.equals("attack") && isFighting){
            if(!currentRoom.getEnemy().damageEnemy(attack(player.returnWeapon())))
            {
                player.changeHealth(currentRoom.getEnemy().attack(player.getAgility()));
            }
            else
            {
                currentRoom.removeEnemy();
                isFighting = false;
            }
            return false;
        }
        else if(commandWord.equals("attack")){
            System.out.println("Es ist kein Gegner mit dir im Raum den du angreifen könntest.");
        }

        if (commandWord.equals("give") && isFighting){
            if(command.hasSecondCommand() && player.hasItem(command.getSecondWord()))
            {
                if(currentRoom.getEnemy().giveItem(command.getSecondWord())) //aktueller Raum muss einen Gegner haben, da der Spieler bereits im Kampf ist
                {
                    currentRoom.removeEnemy();
                }
                player.removeItem(command.getSecondWord());
            }
            else if(command.hasSecondCommand())
            {
                System.out.println("Dieser Gegenstand liegt nicht in ihrem Inventar.");
            }
            else
            {
                System.out.println("Geben Sie einen Gegenstand zum übergeben an.");
            }
            return false;
        }

        if (commandWord.equals("escape") && isFighting){
            if(command.hasSecondCommand()){
                int result = currentRoom.tryingToEscape(command.getSecondWord());
                if(result == 0){
                    System.out.println("Du hast es geschafft zu flüchten.");
                    changeRoom(command);
                    checkForEnemy();
                    isFighting = false;
                }
                else if(result == 1){
                    System.out.println("Du hast es nicht geschafft weg zu laufen, der Gegner hat dich daran gehindert.");
                    player.changeHealth(currentRoom.getEnemy().attack(player.getAgility()));
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
        
        if(commandWord.equals("stats")){
            player.printStats();
            return false;
        }
        
        if(commandWord.equals("heal"))
        {
            player.heal();
            if(isFighting){
                player.changeHealth(currentRoom.getEnemy().attack(player.getAgility()));
            }
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
                if(player.hasItem(command.getSecondWord()))
                {
                    player.getItem(command.getSecondWord()).printAllStats();
                }
                else
                {
                    currentRoom.printItemDescription(command.getSecondWord());
                }
            }
            else{
                currentRoom.printAllItemsInRoom();
            }
        }
        else if (commandWord.equals("inventory")){
            player.printInventory();
        }
        
        else if (commandWord.equals("eat")){
            if(command.hasSecondCommand()){
                player.eat(command.getSecondWord());
            }
            else{
                System.out.println("Es wurde keine Essen bestimmt welches gegessen werden soll");
            }
        }
        else if (commandWord.equals("pickup")){
            if(command.hasSecondCommand()){
                player.pickUp(currentRoom.findItem(command.getSecondWord()), currentRoom);
            }
            else{
                System.out.println("Was soll auf gehoben werden?");
            }
        }
        else if (commandWord.equals("give"))
        {
            System.out.println("Du bist nicht im Kampf.");
        }
        
        // ansonsten: Command nicht erkannt.
        return moechteBeenden;
    }

    /** 
        Überprüft den Command, wenn aktuelle ein Container vom Spieler geöffnet ist und somit nur bestimmte Commande verwendet werden können. 
       **/
    private void handleContainerCommand(Command command)
    {
        if(command.getCommand().equals("put"))
        {
            if(!player.putItemInContainer(currentRoom.getContainer(), command.getSecondWord()))
            {
                System.out.println("Dieser Gegenstand liegt nicht in deinem Inventar!");
            }
        }
        else if(command.getCommand().equals("take"))
        {
            if(!player.pickUp(currentRoom.getContainer().takeItem(command.getSecondWord(), player.hasWeapon()), currentRoom))
            {
                System.out.println("Dieser Gegenstand liegt nicht in " + currentRoom.getContainer().getName());
            }
        }
        else
        {
            System.out.println("Du musst " + currentRoom.getContainer().getName() + " erst schließen bevor du etwas anderes tun kannst.");
        }
    }

    /**
     * Gib Hilfsinformationen aus.
     * Hier geben wir eine etwas alberne und unklare Beschreibung
     * aus, sowie eine Liste der Commandswörter.
     */
    private void hilfstextAusgeben() 
    {
        System.out.println("Sie wissen nicht was zu tun ist, Sie sind allein?");
        System.out.println();
        System.out.println("Ihnen stehen folgende Commands zur Verfügung:");
        parser.printCommands();
    }

    /**
     * Versuche, in eine Richtung zu gehen. Wenn es einen Ausgang gibt,
     * wechsele in den neuen Raum, ansonsten gib eine Fehlermeldung
     * aus.
     * 
     * Überprüft ebenso, ob ein Schlüssel für die Tür benötigt wird.
     */
    private void changeRoom(Command command) 
    {
        if(!command.hasSecondCommand()) {
            // Gibt es kein zweites Wort, wissen wir nicht, wohin...
            System.out.println("Geben Sie eine Richtung an! (z.B. go north)");
            return;
        }

        // Wir versuchen, den Raum zu verlassen.
        Room nextRoom = currentRoom.getExit(command.getSecondWord());

        if (nextRoom == null) {
            System.out.println("Dort ist keine Tür!");
        }
        else if(nextRoom.checkIfLocked() && !player.hasKeyOfType("Door"))
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
     * "quit" wurde eingegeben. Überprüfe den Rest des Commands,
     * ob das Spiel wirklich beendet werden soll.
     * @return  true, wenn der Command das Spiel beendet, 
     *          false sonst
     */
    private boolean exit(Command command) 
    {
        if(command.hasSecondCommand()) {
            System.out.println("Was soll beendet werden?");
            return false;
        }
        else {
            return true;  // Das Spiel soll beendet werden.
        }
    }

    /**
     * Überprüfe, ob sich ein Gegner im neu betretenen Raum befindet und setze in dem Fall den Spieler in den Kampf-Modus.
     */
    
    private void checkForEnemy(){
        if(currentRoom.hasEnemy()){
            System.out.println("Es befindet sich ein Gegner mit dir im Raum.");
            System.out.println("Zur Verfügung stehende Befehle: attack, escape {direction}, give {item}, enemy, stats, heal");
            isFighting = true;
        }
    }

    /**
     * Veranlasse den Angriff an einen Gegner und überprüfe, ob der Spieler überhaupt eine Waffe hat.
     * Der Angriff ist nur für eine bestimmte Wahrscheinlichkeit erfolgreich.
     */
    
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

    /**
     * Ist für das Ausführen des Programmes nach dem Exportieren wichtig.
     */
    public static void main(String args[]){
        Game myGame = new Game();
        myGame.spielen();
    }
}
