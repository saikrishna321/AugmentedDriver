package com.salesforceiq.augmenteddriver.util;

import com.google.common.base.Preconditions;
import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebElement;

public class MobileUtil {

    private static final int VERTICAL_OFFSET = 10;
    private static final int BIG_NUMBER = 9999999;
    private static final int DEFAULT_DURATION = 1000;
    private static final int DEFAULT_TAP_DURATION = 500;

    public static WebElement swipeUpWaitVisible(AppiumDriver driver,
                                                AugmentedFunctions<?> augmentedFunctions,
                                                By swipeElement,
                                                By elementVisible) {
        return swipeVerticalWaitVisible(driver, augmentedFunctions, swipeElement,
                elementVisible, - BIG_NUMBER, 5, DEFAULT_DURATION);
    }

    public static WebElement tap(AppiumDriver driver, AugmentedFunctions<?> augmentedFunctions, By by, int waitTimeInSeconds) {
        WebElement element = augmentedFunctions.findElementPresentAfter(by, waitTimeInSeconds);
        tap(driver, element, DEFAULT_TAP_DURATION);
        return  element;
    }

    public static WebElement tap(AppiumDriver driver, AugmentedFunctions<?> augmentedFunctions,
                                 By by, int offsetX, int offsetY, int waitTimeInSeconds) {
        WebElement element = augmentedFunctions.findElementPresentAfter(by, waitTimeInSeconds);
        driver.tap(1, element.getLocation().getX() + offsetX, element.getLocation().getY() + offsetY, DEFAULT_TAP_DURATION);
        return element;
    }

    public static void tap(AppiumDriver driver, WebElement element, int pressInMilliSeconds) {
        driver.tap(1, element.getLocation().getX(), element.getLocation().getY(), pressInMilliSeconds);
    }

    public static WebElement swipeDownWaitVisible(AppiumDriver driver,
                                                AugmentedFunctions<?> augmentedFunctions,
                                                By swipeElement,
                                                By elementVisible) {
        return swipeVerticalWaitVisible(driver, augmentedFunctions, swipeElement,
                elementVisible, BIG_NUMBER, 5, DEFAULT_DURATION);
    }

    public static void swipeUp(AppiumDriver driver,
                               AugmentedFunctions<?> augmentedFunctions,
                               By swipeBy) {
        swipeVertical(driver, augmentedFunctions, swipeBy, -BIG_NUMBER, DEFAULT_DURATION);
    }

    public static void swipeDown(AppiumDriver driver,
                               AugmentedFunctions<?> augmentedFunctions,
                               By swipeBy) {
        swipeVertical(driver, augmentedFunctions, swipeBy, BIG_NUMBER, DEFAULT_DURATION);
    }


    public static void swipeVertical(AppiumDriver driver,
                                     AugmentedFunctions<?> augmentedFunctions,
                                     By swipeBy,
                                     int offset,
                                     int duration) {
        Preconditions.checkNotNull(driver);
        Preconditions.checkNotNull(augmentedFunctions);
        Preconditions.checkNotNull(swipeBy);
        WebElement elementPresent = augmentedFunctions.findElementPresent(swipeBy);
        int x = elementPresent.getLocation().getX() + elementPresent.getSize().getWidth() / 2;
        int y = elementPresent.getLocation().getY() + elementPresent.getSize().getHeight() /  2;
        int swipe = getVerticalOffset(driver, y, offset);
        driver.swipe(x, y, x, swipe, duration);
    }

    public static WebElement swipeVerticalWaitVisible(AppiumDriver driver,
                                    AugmentedFunctions<?> augmentedFunctions,
                                    By swipeElement,
                                    By elementVisible,
                                    int offset,
                                    int quantity,
                                    int duration) {
        Preconditions.checkNotNull(driver);
        Preconditions.checkNotNull(augmentedFunctions);
        Preconditions.checkNotNull(swipeElement);
        WebElement elementPresent = augmentedFunctions.findElementPresent(swipeElement);
        int x = elementPresent.getLocation().getX() + elementPresent.getSize().getWidth() / 2;
        int y = elementPresent.getLocation().getY() + elementPresent.getSize().getHeight() /  2;

        int swipe = getVerticalOffset(driver, y, offset);

        for(int iteration = 0; iteration < quantity; iteration++) {
            driver.swipe(x, y, x, swipe, duration);
            if (augmentedFunctions.isElementVisibleAfter(elementVisible, 3)) {
                return augmentedFunctions.findElementVisible(elementVisible);
            }
        }
        throw new AssertionError(String.format("Swiped %s with an offest of %s times but element %s not found",
                        quantity, offset, elementVisible));
    }

    public static void swipeFullRightAfter(AppiumDriver driver, AugmentedFunctions<?> augmentedFunctions, WebElement element,
                                           int waitTimeInSeconds) {
        Preconditions.checkNotNull(driver);
        Preconditions.checkNotNull(augmentedFunctions);
        Preconditions.checkNotNull(element);
        Dimension size = driver.manage().window().getSize();
        int to = size.getWidth() * 85 / 100;
        int from = size.getWidth() * 15 / 100;
        int y = element.getLocation().getY() + element.getSize().getHeight() / 2;
        driver.swipe(from, y, to, y, DEFAULT_DURATION);
    }

    public static void swipeFullRightAfter(AppiumDriver driver, AugmentedFunctions<?> augmentedFunctions, By by, int waitTimeInSeconds) {
        Preconditions.checkNotNull(by);
        Preconditions.checkNotNull(driver);
        Preconditions.checkNotNull(augmentedFunctions);
        WebElement element = augmentedFunctions.findElementPresentAfter(by, waitTimeInSeconds);
        swipeFullRightAfter(driver, augmentedFunctions, element, waitTimeInSeconds);
    }

    public static void swipeFullLeftAfter(AppiumDriver driver, AugmentedFunctions<?> augmentedFunctions, WebElement element,
                                           int waitTimeInSeconds) {
        Preconditions.checkNotNull(driver);
        Preconditions.checkNotNull(augmentedFunctions);
        Preconditions.checkNotNull(element);
        Dimension size = driver.manage().window().getSize();
        int from = size.getWidth() * 85 / 100;
        int to = size.getWidth() * 15 / 100;
        int y = element.getLocation().getY() + element.getSize().getHeight() / 2;
        driver.swipe(from, y, to, y, DEFAULT_DURATION);
    }

    public static void swipeFullLeftAfter(AppiumDriver driver, AugmentedFunctions<?> augmentedFunctions, By by, int waitTimeInSeconds) {
        Preconditions.checkNotNull(by);
        Preconditions.checkNotNull(driver);
        Preconditions.checkNotNull(augmentedFunctions);
        WebElement element = augmentedFunctions.findElementPresentAfter(by, waitTimeInSeconds);
        swipeFullLeftAfter(driver, augmentedFunctions, element, waitTimeInSeconds);
    }

    private static int getVerticalOffset(AppiumDriver driver, int y, int offset) {
        if (y + offset < VERTICAL_OFFSET) {
            return VERTICAL_OFFSET;
        } else if (y + offset > driver.manage().window().getSize().getHeight() - VERTICAL_OFFSET) {
            return driver.manage().window().getSize().getHeight() - VERTICAL_OFFSET;
        } else {
            return y + offset;
        }
    }
}
