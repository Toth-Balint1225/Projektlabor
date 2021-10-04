//package fluent; // terminálból futtatáskor lehet ki kell kommentezni


/*
    =Fluent interface 1. lehetőség=
    lényeg:
    o egy egyszerű kezelőfelület, mindegy hogy most a backend nincs megcsinálva
    o részbeállítások: pl. a title beállítható legyen a saját függvényeivel, de utána visszaugrik a Chart-hoz
      -> ez úgy lenne megoldható, hogy a Title benne van a Chart osztályban
      - a title rendelkezik a saját tulajdonságaival, meg az azokat beállító függvényekkel
      - title "beállítási módból" a makeTitle() fv. lép vissza Chart beállításba
    o példa: egy Chart, aminek van:
      - cím, title : Title
      - x tengely limit, xlim : int
      - y tengely limit, ylim : int
      - grid bekapcsolva, isGrid : boolean
      - x tengely címke: xlabel : String
      - y tengely címke: ylabel : String
    o a Title osztálynak van:
      - maga a szöveg, title : String
      - szín, color : String
      - betűméret, size : int
      - félkövér, isBold : boolean
 */

/*
    -beállítások működése-
    o felfogható gráfként:
      - a fő osztály alapbeállításai pontok a gráfban, amiknek nincs gyereke
      - a fő osztálynak lehetnek komplex beállításai (pl: title), ezek olyan pontok, amiknek van gyereke
      - amikor beugrik egy olyan pontba aminek gyerekei vannak, mindig kell egy olyan gyerekének lennie
        ami visszadobja egy szinttel feljebb

                 (Chart) <-------------------------------------------+
                    |                                                |
         +----+-----+--------+--------+-------+                      |
         |    |     |        |        |       |                      |
         v    v     v        v        v       v                      |
    (xlim) (ylim) (isGrid) (xlabel) (ylabel) [title]                 |
                                               |                     |
                           +------+------+-----+---------+           |
                           |      |      |     |         |           |
                           v      v      v     v         v           |
                       (title) (color) (size) (isBold) <makeTitle>---+
       - itt a () a végpont, ezek mindig a saját osztályukat adják vissza és egyszerű (egy adattag) beállítások
       - a [] az albeállítás, ez a beállítandó paraméter osztályát adja vissza, nem csinál semmit, csak "üzemmódot" vált
       - a <> pedig a függvény, ami visszatesz az egy szinttel feljebbi beállításokra, ez is csak üzemmódváltáshoz
    o a gráf megjelenik az osztálystruktúrában:
      - a Chart osztály tartalmazza a Title-t
      - a () pontok függvények, amik beállítanak és visszaadják a példányukat
      - a [] pontok függvények, amik létrehozzák az adattagot és visszaadják azt, átváltunk albeállításba
      - a <> pontok függvények, amik visszaadják a felső osztályukat, és ezzel visszalépnek albeállításból
    o emiatt lényegében a végtelenségig komplikált beállítási struktúrát létre lehet hozni
 */

// ezt állítgatjuk be
public class Chart {

    // a Chartnak a title tulajdonságát úgy állítjuk részletesen, hogy
    // a Title fizikailag benne van a Chart osztályba, így a gazda osztályt vissza tudja
    // adni a fluent interfacebe
    public class Title {
        // Title adattagok
        private String title;
        private String color;
        private int size;
        private boolean isBold;

        // konstruktor a hard-codeed default értékekkel
        public Title() {
            this.title = null;
            this.size = 8;
            this.color = "blue";
            this.isBold = false;
        }

        // demonstrációs függvény, csak hogy csináljon is valamit
        @Override
        public String toString() {
            return title + ", " + size + ", " + color + ", " + isBold;
        }

        //-----------------------------------------------------
        // ezek itt a () pontok, alapvető beállításokkal
        public Title withTitleString(String title) {
            this.title = title;
            return this;
        }

        public Title withSize(int size) {
            this.size = size;
            return this;
        }

        public Title withColor(String color) {
            this.color = color;
            return this;
        }

        public Title withBold() {
            this.isBold = true;
            return this;
        }
        //-----------------------------------------------------

        // ez a <> pont, visszaléptet egy szinttel feljebb
        public Chart makeTitle() {
            return Chart.this;
        }

    }

    // a Chart adattagok
    private Title title;  // ez egy komplex beállítás
    // atomi beállítások
    private int xlim;
    private int ylim;
    private boolean isGrid;
    private String xlabel, ylabel;

    // konstruktor a default értékekkel
    public Chart() {
        title = null;
        xlim = 10;
        ylim = 10;
        isGrid = false;
        xlabel = "x";
        ylabel = "y";
    }

    // demo
    @Override
    public String toString() {
        return title.toString() + "; " + xlabel + ", " + ylabel + ", " + xlim + ", " + isGrid;
    }

    // ez a [] pont, létrehozza a title-t és visszaadja -> üzemmódot vált
    // innentől a Title osztály intézi magát, aztán a makeTitle() jön vissza
    public Title withTitle() {
        this.title = new Title();
        return this.title;
    }

    //-----------------------------------------
    // ezek a () pontok az egyszerű beállításokkal
    public Chart withXlim(int xlim) {
        this.xlim = xlim;
        return this;
    }

    public Chart withYlim(int ylim) {
        this.ylim = ylim;
        return this;
    }

    public Chart withGrid() {
        this.isGrid = true;
        return this;
    }

    public Chart withXlabel(String xlabel) {
        this.xlabel = xlabel;
        return this;
    }

    public Chart withYlabel(String ylabel) {
        this.ylabel = ylabel;
        return this;
    }
    //-----------------------------------------
    // a Chart-nak a <> pontja
    // ez opcionális, de van
    public Chart makeChart() {
        printChart();
        return this;
    }

    // demo funkcionalitás
    public void printChart() {
        System.out.println(toString());
    }

    public static void main(String[] args) {

        // használat:
        Chart chart = new Chart()                          // létrehozás
                .withTitle()                               // belépés title beállítási módba
                  .withTitleString("this is the title")    // title beállítások
                  .withBold()
                  .withSize(24)
                  .makeTitle()                             // visszalépés title beállítási módból
                .withXlim(100)                             // egyéb Chart beállítások
                .withGrid()
                .withXlabel("t [s]")
                .withYlabel("v(t) [m/s]");                 // kész a cuccos, lehet használni
                // esetleg ehy .makeChart() utána tehető, de most elrakjuk későbbre

        // demo használat
        // innentől jöhet a client code
        chart.printChart();

        // lehet így is
        Chart other = new Chart()
                .withXlim(100)
                .withYlim(100)
                .withTitle()
                  .withTitleString("other chart")
                  .makeTitle()
                .makeChart();  // megint csak opcionális

    }
}
