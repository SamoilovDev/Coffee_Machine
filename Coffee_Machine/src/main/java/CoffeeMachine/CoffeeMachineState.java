package CoffeeMachine;

public enum CoffeeMachineState {
    WAITING_FOR_INPUT(true, "Write action (buy, fill, take, remaining, exit):") {
        @Override
        public CoffeeMachineState next(CoffeeMachine coffeeMachine) {
            return switch (coffeeMachine.getInput()) {
                case "buy" -> CHOOSING_BUYING_OPTIONS;
                case "fill" -> FILLING_WATER;
                case "take" -> TAKING_MONEY;
                case "remaining" -> PRINTING_REMAINING_RESOURCES;
                case "exit" -> POWERED_OFF;
                default -> WAITING_FOR_INPUT;
            };
        }
    },

    CHOOSING_BUYING_OPTIONS(true, "What do you want to buy? 1 - espresso, 2 - latte, 3 - cappuccino, back - to main menu:") {
        @Override
        public CoffeeMachineState next(CoffeeMachine coffeeMachine) {
            if (coffeeMachine.isCoffeeChosen()) {
                coffeeMachine.selectCoffee();
                return BUYING_COFFEE;
            }
            return WAITING_FOR_INPUT;
        }
    },

    BUYING_COFFEE {
        @Override
        public CoffeeMachineState next(CoffeeMachine coffeeMachine) {
            coffeeMachine.buyCoffee();
            return WAITING_FOR_INPUT;
        }
    },

    FILLING_WATER(true, "Write how many ml of water do you want to add:") {
        @Override
        public CoffeeMachineState next(CoffeeMachine coffeeMachine) {
            coffeeMachine.fillWater();
            return FILLING_MILK;
        }
    },

    FILLING_MILK(true, "Write how many ml of milk do you want to add:") {
        @Override
        public CoffeeMachineState next(CoffeeMachine coffeeMachine) {
            coffeeMachine.fillMilk();
            return FILLING_COFFEE_BEANS;
        }
    },

    FILLING_COFFEE_BEANS(true, "Write how many grams of coffee beans do you want to add:") {
        @Override
        public CoffeeMachineState next(CoffeeMachine coffeeMachine) {
            coffeeMachine.fillBeans();
            return FILLING_CUPS;
        }
    },

    FILLING_CUPS(true, "Write how many disposable cups of coffee do you want to add:") {
        @Override
        public CoffeeMachineState next(CoffeeMachine coffeeMachine) {
            coffeeMachine.fillCups();
            return WAITING_FOR_INPUT;
        }
    },

    TAKING_MONEY {
        @Override
        public CoffeeMachineState next(CoffeeMachine coffeeMachine) {
            coffeeMachine.takeMoney();
            return WAITING_FOR_INPUT;
        }
    },

    PRINTING_REMAINING_RESOURCES {
        @Override
        public CoffeeMachineState next(CoffeeMachine coffeeMachine) {
            coffeeMachine.remaining();
            return WAITING_FOR_INPUT;
        }
    },

    POWERED_OFF {
        @Override
        public CoffeeMachineState next(CoffeeMachine coffeeMachine) {
            System.exit(0);
            return POWERED_OFF;
        }
    };

    private final boolean requiredInput;
    private final String stateMessage;

    CoffeeMachineState() {
        requiredInput = false;
        stateMessage = null;
    }

    CoffeeMachineState(boolean requiredInput, String stateMessage) {
        this.requiredInput = requiredInput;
        this.stateMessage = stateMessage;
    }

    public boolean isRequiredInput() {
        return requiredInput;
    }

    public String getStateMessage() {
        return stateMessage;
    }

    public abstract CoffeeMachineState next(CoffeeMachine coffeeMachine);
}