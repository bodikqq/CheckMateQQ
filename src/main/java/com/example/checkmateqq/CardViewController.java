    package com.example.checkmateqq;

    import com.example.checkmateqq.triedy.Card;
    import com.example.checkmateqq.triedy.User;
    import javafx.event.ActionEvent;
    import javafx.fxml.FXML;
    import javafx.scene.control.Button;
    import javafx.scene.control.Label;
    import javafx.scene.control.TextField;
    import javafx.scene.control.TextFormatter;
    import javafx.stage.Stage;

    import java.util.function.UnaryOperator;
    import java.util.regex.Pattern;

    public class CardViewController {

        @FXML
        private TextField Amount;

        @FXML
        private TextField CvvCode;

        @FXML
        private TextField ExpDate;

        @FXML
        private TextField cardNumber;
        @FXML
        private Label labelOnCardNum;
        private CardDao cardDao = DaoFactory.INSTANCE.getCardDao();
        private UserDao userdDao = DaoFactory.INSTANCE.getUserDao();
        private User user;
        private int length = 0;

        @FXML
        void confirm(ActionEvent event) throws EntityNotFoundException {
            String cardNum = cardNumber.getText().replaceAll("[.\\s]", "");
            if (cardNum.length() != 16) {
                return;
            }
            int date = Integer.parseInt(ExpDate.getText().replaceAll("/",""));
            int amount = Integer.parseInt(Amount.getText().replaceAll("/",""));
            int cvv = Integer.parseInt(CvvCode.getText());
            if(CvvCode.getText().length() == 3 && ExpDate.getText().replaceAll("/","").length() == 4){
                Card card = new Card(cardNum,cvv,date);
                int id = cardDao.saveCard(card);
                userdDao.setCardId(user.getId(),id);
                userdDao.updateBalance(user.getId(), Integer.parseInt(Amount.getText()));
                Stage stage = (Stage) Amount.getScene().getWindow();
                stage.close();
            }
        }
        UnaryOperator<TextFormatter.Change> filtermm = change -> {
            String newText = change.getControlNewText();

            // Allow only digits
            if (!Pattern.matches("[0-9]*", change.getText())) {
                return null;
            }

            // Allow only up to 4 digits (MMYY)
            if (newText.length() > 5) {
                return null;
            }

            // Insert a slash after the first two digits
            if (newText.length() == 3) {
                change.setText( "/" + change.getText());
                change.setCaretPosition(change.getCaretPosition() + 1); // Move caret after the space
                change.setAnchor(change.getAnchor() + 1);
            }

            return change;
        };
        public void initialize() throws EntityNotFoundException {
            // Assuming you have the filters and formatters defined as before...

            TextFormatter<String> textFormatter = new TextFormatter<>(filter);
            Amount.setTextFormatter(textFormatter);


            TextFormatter<String> textFormatter6 = new TextFormatter<>(filterOfLength3);
            CvvCode.setTextFormatter(textFormatter6);



            TextFormatter<String> textFormatter7 = new TextFormatter<>(filtermm);
            ExpDate.setTextFormatter(textFormatter7);



            TextFormatter<String> textFormatter5 = new TextFormatter<>(filterForCardNumber);
            cardNumber.setTextFormatter(textFormatter5);
        }


        UnaryOperator<TextFormatter.Change> filterForCardNumber = change -> {
            String newText = change.getControlNewText();
            if (!Pattern.matches("[0-9]*", change.getText())) {
                return null;
            }
            if (change.isDeleted()) {
                String nulas = "0000 0000 0000 0000";
                // Handle character deletion


                System.out.println(newText);


                if (newText.length() == 0) labelOnCardNum.setText(nulas);
                else {
                    labelOnCardNum.setText(nulas.substring(newText.length()));
                }
                return change;

            } else if(change.isAdded()){

                newText = change.getControlNewText().replaceAll("[.\\s]", "");;
                //if((newText.length()-1)%4 == 0)
                if (newText.length() == 17) {
                    return null;
                }

                if (labelOnCardNum.getText().length() > 1) {
                    String mm = labelOnCardNum.getText().substring(1);
                    labelOnCardNum.setText(mm);
                }
    //            if ((((newText.length() - 1) % 4) == 0) && (newText.length()  != 1)) {
    //                System.out.println("wtfffff");
    //                change.setText(" " + change.getText());
    //                if (labelOnCardNum.getText().length() > 1) {
    //                    String mm = labelOnCardNum.getText().substring(1);
    //                    labelOnCardNum.setText(mm);
    //                } else {
    //                    labelOnCardNum.setText("");
    //                }
    //                change.setCaretPosition(change.getCaretPosition() + 1); // Move caret after the space
    //                change.setAnchor(change.getAnchor() + 1);
    //                return change;
    //            }
                if (newText.length() % 4 == 0 && newText.length() != 16) {
                    change.setText(change.getText() + " ");
                    if (labelOnCardNum.getText().length() > 1) {
                        String mm = labelOnCardNum.getText().substring(1);
                        labelOnCardNum.setText(mm);
                    } else {
                        labelOnCardNum.setText("");
                    }

                    change.setCaretPosition(change.getCaretPosition() + 1); // Move caret after the space
                    change.setAnchor(change.getAnchor() + 1);
                    return change;
                } else if(newText.length() == 16){
                    labelOnCardNum.setText("");
                    return change;
                }
                    else {
                    return change;
                }
            }
            else return change;
        };

        UnaryOperator<TextFormatter.Change> filter = change -> {
            String newText = change.getControlNewText();
            if (Pattern.matches("[0-9]*", newText)) {
                return change;
            } else {
                return null;
            }
        };
        UnaryOperator<TextFormatter.Change> filterOfLength3 = change -> {
            String newText = change.getControlNewText();
            if (!Pattern.matches("[0-9]*", change.getText())) {
                return null;
            }
            newText = change.getControlNewText();

                if(newText.length() > 3)return null;
                return change;
        };

        UnaryOperator<TextFormatter.Change> filterOfLength4 = change -> {
            String newText = change.getControlNewText();
            if (!Pattern.matches("[0-9]*", change.getText())) {
                return null;
            }
            if(newText.length() > 4)return null;
            return change;
        };
        void setUser(User user) {
            this.user = user;
        }
    }
