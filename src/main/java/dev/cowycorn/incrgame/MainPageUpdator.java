package dev.cowycorn.incrgame;

public class MainPageUpdator implements Runnable{
    ControllerWithCurrencyInfo mainController;

    public MainPageUpdator(ControllerWithCurrencyInfo mainController) {
        this.mainController = mainController;
    }

    @Override
    public void run() {
        mainController.updateLabels();
    }
}
