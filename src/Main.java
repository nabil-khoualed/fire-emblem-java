public class Main {
    public static void afficherNomEtPv(Unite unite) {
        System.out.println(unite.typeUnite() + " " + unite.getName() + " : " + unite.afficherHp());
    }

    public static void main(String[] args) {
        Epeiste chrom = new Epeiste("Chrom", 24, 8);
        Cavalier frederick = new Cavalier("Frederick", 30, 7);
        Mage robin = new Mage("Robin", 22, 9);
        Clerc lissa = new Clerc("Lissa", 18);

        Epeiste vaike = new Epeiste("Vaike", 25, 6);

        afficherNomEtPv(chrom);
        afficherNomEtPv(frederick);
        afficherNomEtPv(robin);
        afficherNomEtPv(lissa);
        afficherNomEtPv(vaike);
    }
}