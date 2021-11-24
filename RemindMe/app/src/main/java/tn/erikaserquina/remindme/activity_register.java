package tn.erikaserquina.remindme;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class activity_register extends AppCompatActivity {

    EditText editName;
    EditText editEmail;
    EditText editPassword;
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_register );

        editName = findViewById( R.id.editRegName );
        editEmail = findViewById( R.id.editRegEmail );
        editPassword = findViewById( R.id.editRegPassword );
        mAuth = FirebaseAuth.getInstance();

    }


    public void register(View view) {
        String name, email, password;

        name = editName.getText().toString().trim();
        email = editEmail.getText().toString().trim();
        password = editPassword.getText().toString().trim();

        mAuth.createUserWithEmailAndPassword( email,password )
                .addOnCompleteListener( new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if (task.isSuccessful()){
                            User user = new User (name, email, password);

                            FirebaseDatabase.getInstance().getReference("Users")
                                    .child( FirebaseAuth.getInstance().getCurrentUser().getUid())
                                    .setValue(user).addOnCompleteListener( new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()){
                                        Toast.makeText(activity_register.this, "Registration is Succesful", Toast.LENGTH_LONG).show();
                                    } else {
                                        Toast.makeText(activity_register.this, "Registration is Failed, Please try again.", Toast.LENGTH_LONG).show();
                                    }
                                }
                            } );
                        }else {
                            Toast.makeText(activity_register.this, "Registration Failed", Toast.LENGTH_LONG).show();
                        }
                    }
                } );
    }

    public void backToLog(View view) {
        Intent intent = new Intent(activity_register.this, MainActivity.class);
        startActivity( intent );
    }
}