import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.util.Stack;
import javax.swing.*;

public class Calculator extends JFrame {
    JButton digits[] = {
            new JButton(" 0 "),
            new JButton(" 1 "),
            new JButton(" 2 "),
            new JButton(" 3 "),
            new JButton(" 4 "),
            new JButton(" 5 "),
            new JButton(" 6 "),
            new JButton(" 7 "),
            new JButton(" 8 "),
            new JButton(" 9 ")
    };

    JButton operators[] = {
            new JButton(" + "),
            new JButton(" - "),
            new JButton(" * "),
            new JButton(" / "),
            new JButton(" = "),
            new JButton(" C "),
            new JButton("FPE"),
            new JButton("space")//forma poloneza explicita
    };

    String oper_values[] = {"+", "-", "*", "/", "=", "", "FPE", " "};

    String value;
    char operator;

    JTextArea area = new JTextArea(4, 5);

    public static void main(String[] args) {
        Calculator calculator = new Calculator();
        calculator.setSize(230, 260);
        calculator.setTitle(" Java-Calc, PP Lab1 ");
        calculator.setResizable(false);
        calculator.setVisible(true);
        calculator.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public Calculator() {
        add(new JScrollPane(area), BorderLayout.NORTH);
        JPanel buttonpanel = new JPanel();
        buttonpanel.setLayout(new FlowLayout());

        for (int i=0;i<10;i++)
            buttonpanel.add(digits[i]);

        for (int i=0;i<8;i++)
            buttonpanel.add(operators[i]);

        add(buttonpanel, BorderLayout.CENTER);
        area.setForeground(Color.BLACK);
        area.setBackground(Color.WHITE);
        area.setLineWrap(true);
        area.setWrapStyleWord(true);
        area.setEditable(false);

        for (int i=0;i<10;i++) {
            int finalI = i;
            digits[i].addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent actionEvent) {
                    area.append(" "+Integer.toString(finalI)+" ");
                }
            });
        }

        for (int i=0;i<8;i++){
            int finalI = i;
            operators[i].addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent actionEvent) {
                    if (finalI == 6){
                        String input = area.getText().trim();
                        double result = evalaureFormaPolonezaExplicita(input);
                        area.setText(Double.toString(result));
                    }
                    else
                    if (finalI == 5)
                        area.setText("");
                    else
                    if (finalI == 4) {
                        String lhs;
                        String rhs;
                        try {
                            lhs = area.getText().substring(0, area.getText().indexOf(operator + ""));
                            rhs = area.getText().substring(area.getText().indexOf(operator + "") + 1, area.getText().length());
                            switch (operator) {
                                case '+': area.append(" = " + ((Double.parseDouble(lhs) + Double.parseDouble(rhs)))); break;
                                case '-': area.append(" = " + ((Double.parseDouble(lhs) - Double.parseDouble(rhs)))); break;
                                case '/': area.append(" = " + ((Double.parseDouble(lhs) / Double.parseDouble(rhs)))); break;
                                case '*': area.append(" = " + ((Double.parseDouble(lhs) * Double.parseDouble(rhs)))); break;
                                default: area.setText(" "); break;
                            }
                        } catch (Exception e) {
                            area.setText(" !!!Probleme!!! ");
                        }
                    }
                    else {
                        area.append(oper_values[finalI]);
                        operator = oper_values[finalI].charAt(0);
                    }
                }
            });
        }

    }
    public double evalaureFormaPolonezaExplicita(String input){
        Stack<Double> stack = new Stack<Double>();
        String tokens[] = input.split(" ");

            for (int i = tokens.length - 1; i >= 0; i--){
                String token = tokens[i];
                if(isNumeric(token)){
                    stack.push(Double.parseDouble(token));
                } else if (token.equals("")) {
                    continue;
                } else {

                    if(stack.size()<2) return Double.NaN;
                    double a = stack.pop();
                    double b = stack.pop();

                    switch (token) {
                        case "+":
                            stack.push(a + b);
                            break;
                        case "-":
                            stack.push(a - b);
                            break;
                        case "*":
                            stack.push(a * b);
                            break;
                        case "/":
                            stack.push(a / b);
                            break;
                        default:
                            area.setText("Probleme!!!");
                    }
                }
            }
        return stack.pop();
        //return stack.pop();
    }
    public boolean isNumeric(String str) {
        try {
            Double.parseDouble(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}