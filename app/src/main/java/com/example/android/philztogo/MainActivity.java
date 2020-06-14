package com.example.android.philztogo;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity {
    int quantity = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /** This determines the checked state of the checkbox. */
    public void onCheckboxClicked (View view) {
        boolean checked = ((CheckBox) view).isChecked();
    }

    /**
     * This determines the selected state of the radio button.
     */
    public void onRadioButtonSelected(View view) {
        // Check if radio button is clicked for Home group
        boolean selected = ((RadioButton)view).isChecked();
    }

    /**
     * This method is called when the order button is clicked.
     */
    public void submitOrder(View view) {
        // Figure out if user wants whipped cream topping
        CheckBox whippedCreamCheckBox = (CheckBox) findViewById(R.id.whipped_cream_checkbox);
        boolean hasWhippedCream = whippedCreamCheckBox.isChecked();

        // Figure out if user wants chocolate topping
        CheckBox chocolateCheckBox = (CheckBox) findViewById(R.id.chocolate_checkbox);
        boolean hasChocolate = chocolateCheckBox.isChecked();

        // Figure out if user wants cinnamon topping
        CheckBox cinnamonCheckBox = (CheckBox) findViewById(R.id.cinnamon_checkbox);
        boolean hasCinnamon = cinnamonCheckBox.isChecked();

        // Figure out if user wants hot drink
        RadioButton hotRadioButton = (RadioButton) findViewById(R.id.hot_radiobutton);
        boolean hasHot = hotRadioButton.isChecked();

        // Figure out if user wants cold drink
        RadioButton coldRadioButton = (RadioButton) findViewById(R.id.cold_radiobutton);
        boolean hasCold = coldRadioButton.isChecked();

        // Figure out if user wants small size
        RadioButton smallRadioButton = (RadioButton) findViewById(R.id.small_radiobutton);
        boolean hasSmall = smallRadioButton.isChecked();

        // Figure out if user wants medium size
        RadioButton mediumRadioButton = (RadioButton) findViewById(R.id.medium_radiobutton);
        boolean hasMedium = mediumRadioButton.isChecked();

        // Figure out if user wants large
        RadioButton largeRadioButton = (RadioButton) findViewById(R.id.large_radiobutton);
        boolean hasLarge = largeRadioButton.isChecked();

        // Show error toast if no type is selected
        if (hasHot == false && hasCold == false) {
            // Show an error message as a toast
            Toast.makeText(this, "Please select a coffee type.", Toast.LENGTH_SHORT).show();
            // Exit this method early because there's nothing left to do
            return;
        }

        // Show error toast if no size is selected
        if (hasSmall == false && hasMedium == false && hasLarge == false) {
            // Show an error message as a toast
            Toast.makeText(this, "Please choose a size.", Toast.LENGTH_SHORT).show();
            // Exit this method early because there's nothing left to do
            return;
        }

        // Get the name the user has input
        EditText nameField = findViewById(R.id.name);
        String name = nameField.getText().toString();

        // Show error toast if no name is provided
        if (TextUtils.isEmpty(name)) {
            // Show an error message as a toast
            Toast.makeText(this, "Please enter a name for your order.", Toast.LENGTH_SHORT).show();
            // Exit this method early because there's nothing left to do
            return;
        }

        // Create order summary based on user inputs
        String message = createOrderSummary(hasSmall, hasMedium, hasLarge, hasCold, hasHot, hasWhippedCream, hasChocolate, hasCinnamon, name);

        //TextView finalOrder = (TextView) findViewById(R.id.create_order_summary);
                //finalOrder.setText(message);

        // Set up the alert builder
        AlertDialog.Builder builder = new AlertDialog.Builder(this, R.style.AlertDialogTheme);
        builder.setTitle("Order Summary for " + (name));
        // Display confirmation of the order summary message
        builder.setMessage(message);
        // Add one button
        builder.setPositiveButton("Confirm", null);
        // Create and show the alert dialog
        AlertDialog dialog = builder.create();
        dialog.show();

    }

    /**
     * This creates an order summary.
     * @param addCinnamon if user wants cinnamon
     * @param addWhippedCream if user wants whipped cream
     * @param addChocolate if user wants chocolate
     * @param name displays name of user
     * @param hasCold if user wants cold drink
     * @param hasHot if user wants hot drink
     * @param hasSmall if user wants small size
     * @param hasMedium if user wants medium size
     * @param hasLarge if user wants medium size
     * @return text summary of the order
     */
    private String createOrderSummary(boolean hasSmall, boolean hasMedium, boolean hasLarge, boolean hasCold, boolean hasHot, boolean addWhippedCream, boolean addChocolate, boolean addCinnamon, String name) {
        String message = "Quantity: " + quantity;

        message += "\nToppings: ";

        if (addWhippedCream == false && addChocolate == false && addCinnamon == false) {
            message += "None";
        } else if (addWhippedCream == true && addChocolate == false && addCinnamon == false) {
            message += "Whipped Cream";
        } else if (addWhippedCream == true && addChocolate == true && addCinnamon == false) {
            message += "Whipped Cream, Chocolate";
        } else if (addWhippedCream == true && addChocolate == false && addCinnamon == true) {
            message += "Whipped Cream, Cinnamon";
        } else if (addWhippedCream == true && addChocolate == true && addCinnamon == true) {
            message += "Whipped Cream, Cinnamon, Chocolate";
        } else if (addWhippedCream == false && addChocolate == false && addCinnamon == true) {
            message += "Cinnamon";
        } else if (addWhippedCream == false && addChocolate == true && addCinnamon == true) {
            message += "Cinnamon, Chocolate";
        } else if (addWhippedCream == false && addChocolate == true && addCinnamon == false) {
            message += "Chocolate";
        }

        if (hasCold == true && hasSmall == true && hasMedium == false && hasLarge == false) {
            message += "\nSmall Iced Coffee";
        } else if (hasHot == true && hasSmall == true && hasMedium == false && hasLarge == false) {
            message += "\nSmall Hot Coffee";
        }

        if (hasCold == true && hasSmall == false && hasMedium == true && hasLarge == false) {
            message += "\nMedium Iced Coffee";
        } else if (hasHot == true && hasSmall == false && hasMedium == true && hasLarge == false) {
            message += "\nMedium Hot Coffee";
        }

        if (hasCold == true && hasSmall == false && hasMedium == false && hasLarge == true) {
            message += "\nLarge Iced Coffee";
        } else if (hasHot == true && hasSmall == false && hasMedium == false && hasLarge == true) {
            message += "\nLarge Hot Coffee";
        }

        message += "\n\nThank you!";
        return message;
    }


    /**
     * This method is called when the + button is clicked.
     */
    public void increment(View view) {
        if (quantity == 5) {
            // Show an error message as a toast
            Toast.makeText(this, "Sorry, you've reached the max order size.", Toast.LENGTH_SHORT).show();
            // Exit this method early because there's nothing left to do
            return;
        } else {
            quantity = quantity + 1;
            displayQuantity(quantity);
        }
    }

    /**
     * This method is called when the - button is clicked.
     */
    public void decrement (View view) {
        if (quantity == 1) {
            // Show an error message as a toast
            Toast.makeText(this, "The minimum order is 1 coffee.", Toast.LENGTH_SHORT).show();
            // Exit this method early because there's nothing left to do
            return;
        } else {
            quantity = quantity - 1;
            displayQuantity(quantity);
        }
    }

    /**
     * This method displays the given quantity value on the screen.
     */
    private void displayQuantity(int number) {
        TextView quantityTextView = (TextView) findViewById(R.id.quantity_text_view);
        quantityTextView.setText("" + number);
    }

}
