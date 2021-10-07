package Players;

public enum CatOptions {
    DEFAULT("Cat.png"),BLUE("CatBlue.png"),GREEN("CatGreen.png");

    String fileName;
    CatOptions(String fileName) {
        this.fileName = fileName;
    }

    public String getFileName() {
        return fileName;
    }
}
