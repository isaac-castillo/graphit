package graphit;

import org.mariuszgromada.math.mxparser.Argument;

public class Function {

    private Argument input;
    private Argument output;

    private Function(Argument input, Argument output) {

        this.input = input;
        this.output = output;
    }

    static Function parse(String string) {

        Argument input = new Argument("x");
        Argument output = new Argument(string, input);
        return new Function(input, output);

    }

    public double evaluate(double input) {

        this.input.setArgumentValue(input);
        return output.getArgumentValue();

    }

}
