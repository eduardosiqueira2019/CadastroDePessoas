package br.com.cadastrodepessoas.util;

import android.content.Context;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.text.method.KeyListener;
import android.text.method.NumberKeyListener;
import android.util.AttributeSet;
import android.widget.EditText;

public class EditTextCep extends androidx.appcompat.widget.AppCompatEditText {
    private boolean isUpdating;

    /*
     * Mapeia a posição do cursor do número de telefone para o número com máscara... 1234567890
     * => 12345-678
     */
    private int positioning[] = { 0, 1, 2, 3, 4, 6, 7, 8, 9 };

    public EditTextCep(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initialize();

    }

    public EditTextCep(Context context, AttributeSet attrs) {
        super(context, attrs);
        initialize();

    }

    public EditTextCep(Context context) {
        super(context);
        initialize();

    }

    public String getCleanText() {
        String text = EditTextCep.this.getText().toString();

        text.replaceAll("[^0-9]*", "");
        return text;

    }

    private void initialize() {

        final int maxNumberLength = 8;
        this.setKeyListener((KeyListener) keylistenerNumber);

        this.setText("     -   ");
        this.setSelection(1);

        this.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable s) {
                String current = s.toString();

                /*
                 * Definindo um sinalizador de que estamos realmente
                 * atualizando o texto, para não precisar atualizá-lo de novo...
                 */
                if (isUpdating) {
                    isUpdating = false;
                    return;

                }

                /* Retira qualquer dígito não numérico da String... */
                String number = current.replaceAll("[^0-9]*", "");
                if (number.length() > 8)
                    number = number.substring(0, 8);
                int length = number.length();

                /* Completa número para 10 caracteres... */
                String paddedNumber = padNumber(number, maxNumberLength);

                /* Separa o número em partes... */
                String part1 = paddedNumber.substring(0, 5);
                String part2 = paddedNumber.substring(5, 8);

                /* Constrói a máscara... */
                String cep = part1 + "-" + part2;

                /*
                 * Desativa evento afterTextChanged através da Flag
                 */
                isUpdating = true;
                EditTextCep.this.setText(cep);

                EditTextCep.this.setSelection(positioning[length]);

            }

            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {

            }

            public void onTextChanged(CharSequence s, int start, int before,
                                      int count) {

            }
        });
    }

    protected String padNumber(String number, int maxLength) {
        String padded = new String(number);
        for (int i = 0; i < maxLength - number.length(); i++)
            padded += " ";
        return padded;

    }

    private final KeylistenerNumber keylistenerNumber = new KeylistenerNumber();

    private class KeylistenerNumber extends NumberKeyListener {

        public int getInputType() {
            return InputType.TYPE_CLASS_NUMBER
                    | InputType.TYPE_TEXT_FLAG_NO_SUGGESTIONS;

        }

        @Override
        protected char[] getAcceptedChars() {
            return new char[] { '0', '1', '2', '3', '4', '5', '6', '7', '8',
                    '9' };

        }
    }
}