package utils;


public class RecipeStep {
    public String img;
    public String txt;

    public RecipeStep(String img, String txt) {
        this.img = img;
        this.txt = txt;
    }

    @Override
    public String toString() {
        return "RecipeStep{" + "img=" + img + ", txt=" + txt + '}';
    }
}
