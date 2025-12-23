package dev.cowycorn.incrgame;

public class MainPageUpdator implements Runnable{
    MainController mainController;

    public MainPageUpdator(MainController mainController) {
        this.mainController = mainController;
    }

    @Override
    public void run() {
        mainController.updateLabels();
    }
}
