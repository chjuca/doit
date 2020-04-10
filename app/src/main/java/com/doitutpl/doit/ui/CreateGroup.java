package com.doitutpl.doit.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.doitutpl.doit.Models.Email;
import com.doitutpl.doit.Adaptadores.EmailAdapter;
import com.doitutpl.doit.Models.Group;
import com.doitutpl.doit.R;
import com.doitutpl.doit.StaticData;

import java.util.ArrayList;
import java.util.Properties;
import java.util.UUID;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class CreateGroup extends AppCompatActivity {

    ImageButton btnCopy;
    Button btnGenerateKey;
    EditText groupName, emailUser;
    TextView groupKey;
    Context context = this;
    RecyclerView recyclerView;
    EmailAdapter emailAdapter;

    ArrayList<Email> myDataset = new ArrayList<>();

    // Recycler View
    LinearLayoutManager linearLayoutManager;

    // Mail----

    Email email = new Email();
    Session session = null;
    ProgressDialog pdialog = null;
    String rec, subject, textMessage;
    String[] users;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agregar_grupo);

        // Recycler View

        recyclerView = findViewById(R.id.recyclerView);
        linearLayoutManager = new LinearLayoutManager(context);

        recyclerView.setHasFixedSize(false);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setLayoutManager(linearLayoutManager);
        emailAdapter = new EmailAdapter(myDataset, context);
        recyclerView.setAdapter(emailAdapter);


        //=============================

        // Resto de Widgets
        btnCopy = findViewById(R.id.btnCopy);
        groupName = findViewById(R.id.groupName);
        emailUser = findViewById(R.id.emailUser);
        btnGenerateKey = findViewById(R.id.btnCreate);


        btnGenerateKey.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Obtenemos los datos desde la interfaz
                String targetKeyGroup = UUID.randomUUID().toString();
                String targetKeyChat = UUID.randomUUID().toString();
                String targetNameGroup = groupName.getText().toString();

                // ! Este cosntrutor debe usarse obligatoriamente antes de llamar al método .save()
                // Utilizamos este construcor para que agregue al usuario logeado como primer miembro y como admin

                if (targetNameGroup.length() != 0) {
                    Group group = new Group(targetKeyGroup, targetKeyChat, targetNameGroup, "00", StaticData.currentUser);

                    // Guardamos el grupo en la base de datos
                    group.save(context);
                    for (Email email : myDataset) {
                        sendMail(email.getUserEmail(), targetKeyGroup);
                    }
                } else {
                    Toast.makeText(CreateGroup.this, "!Rellene todos los Campos¡", Toast.LENGTH_LONG).show();
                }

            }
        });

        btnCopy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addEmail(0);
                for (Email email : myDataset) {
                    System.out.println(email.getUserEmail());
                }
            }
        });
    }

    private void addEmail(int position) {
        if (email.validateEmail(emailUser.getText().toString())) {
            myDataset.add(position, new Email(emailUser.getText().toString()));
            emailUser.setText("");
            linearLayoutManager.scrollToPosition(position);
            emailAdapter.notifyItemInserted(position);
        } else {
            Toast.makeText(CreateGroup.this, "¡El email ingresado no es valido!", Toast.LENGTH_LONG).show();
            emailUser.setText("");
        }
    }

    public void sendMail(String receptor, String keyGroup) {

        subject = "Invitación de Grupo";
        textMessage = "<p>Usa este enlace para unirte al grupo: <b>" + groupName.getText().toString().toUpperCase() + "</b> en DOIT! </p>" +
                "<br>" +
                "<a href=\"https://www.doit.com/" + keyGroup + "\">Únete aquí</a>";

        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.socketFactory.port", "465");
        props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.port", "465");

        session = Session.getDefaultInstance(props, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication("aqui va el corrreo", "aqui va la contraseña");
            }
        });

        pdialog = ProgressDialog.show(context, "", "Enviando Invitaciones", true);

        pdialog.show();

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            public void run() {
                pdialog.dismiss();
            }
        }, 3000); // 3000 milliseconds delay
        Toast.makeText(CreateGroup.this, "!Grupo creado Exitosamente¡", Toast.LENGTH_LONG).show();

        class RetreiveFeedTask extends AsyncTask<String, Void, String> {

            @Override
            protected String doInBackground(String... params) {

                String user = params[0];
                try {
                    Message message = new MimeMessage(session);
                    message.setFrom(new InternetAddress("DOIT"));
                    message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(user));
                    message.setSubject(subject);
                    message.setContent(textMessage, "text/html; charset=utf-8");
                    Transport.send(message);
                } catch (MessagingException e) {
                    e.printStackTrace();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return null;
            }
        }


    }
}

