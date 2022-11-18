package CoffeeMachine;

import java.util.Scanner;

class CoffeeMachine {
    private int water;
    private int milk;
    private int coffeeBeans;
    private int cups;
    private int money;
    private String input;
    private Coffee coffeeToMake;
    private final Coffee[] availableCoffeeTypes;
    private CoffeeMachineState coffeeMachineState;

    public static void main(String[] args) {
        final Scanner scanner = new Scanner(System.in);
        final Coffee[] coffeeTypes = {Coffee.ESPRESSO, Coffee.LATTE, Coffee.CAPPUCCINO};
        final CoffeeMachine coffeeMachine =
                new CoffeeMachine(400, 540, 120, 9, 550, coffeeTypes);

        while (coffeeMachine.getCoffeeMachineState() != CoffeeMachineState.POWERED_OFF) {
            coffeeMachine.printMessage();
            String input = scanner.nextLine();
            coffeeMachine.nextState(input);
        }
    }

    public CoffeeMachine(int water, int milk, int coffeeBeans, int cups, int money, Coffee[] availableCoffeeTypes) {
        this.water = water;
        this.milk = milk;
        this.coffeeBeans = coffeeBeans;
        this.cups = cups;
        this.money = money;
        this.availableCoffeeTypes = availableCoffeeTypes;
        this.coffeeMachineState = CoffeeMachineState.WAITING_FOR_INPUT;
    }

    public String getInput() {
        return input;
    }

    public CoffeeMachineState getCoffeeMachineState() {
        return coffeeMachineState;
    }

    public void nextState(String input) {
        this.input = input;
        do {
            coffeeMachineState = coffeeMachineState.next(this);
        } while (!coffeeMachineState.isRequiredInput());
    }

    public void printMessage() {
        System.out.println(coffeeMachineState.getStateMessage());
    }

    void selectCoffee() {
        int index = Integer.parseInt(input) - 1;
        coffeeToMake = availableCoffeeTypes[index];
    }

    boolean isCoffeeChosen() {
        return !input.equals("back");
    }

    void buyCoffee() {
        if (canMakeCoffee()) {
            takePayment();
            useResources();
            System.out.println("I have enough resources, making you a coffee!");
        }
    }

    private boolean canMakeCoffee() {
        return isEnoughWater() && isEnoughMilk() && isEnoughCoffeeBeans() && isEnoughCups();
    }

    private boolean isEnoughWater() {
        if (water < coffeeToMake.getWater()) {
            System.out.println("Sorry, not enough water!\n");
            return false;
        } else {
            return true;
        }
    }

    private boolean isEnoughMilk() {
        if (milk < coffeeToMake.getMilk()) {
            System.out.println("Sorry, not enough milk!\n");
            return false;
        } else {
            return true;
        }
    }

    private boolean isEnoughCoffeeBeans() {
        if (coffeeBeans < coffeeToMake.getCoffeeBeans()) {
            System.out.println("Sorry, not enough beans!\n");
            return false;
        } else {
            return true;
        }
    }

    private boolean isEnoughCups() {
        if (cups == 0) {
            System.out.println("Sorry, not enough cups!\n");
            return false;
        } else {
            return true;
        }
    }

    private void takePayment() {
        money += coffeeToMake.getCost();
    }

    private void useResources() {
        water -= coffeeToMake.getWater();
        milk -= coffeeToMake.getMilk();
        coffeeBeans -= coffeeToMake.getCoffeeBeans();
    }

    void fillWater() {
        water += Integer.parseInt(input);
    }

    void fillMilk() {
        milk += Integer.parseInt(input);
    }

    void fillBeans() {
        coffeeBeans += Integer.parseInt(input);
    }

    void fillCups() {
        cups += Integer.parseInt(input);
    }

    void takeMoney() {
        System.out.printf("I gave you $%d%n", money);
        money = 0;
    }

    void remaining() {
        System.out.printf("""
                
                The coffee machine has:
                %d ml of water
                %d ml of milk
                %d g of coffee beans
                %d disposable cups
                $%d of money%n
                """, water, milk, coffeeBeans, cups, money);
    }

    
}
