package com.doitutpl.doit.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.doitutpl.doit.Models.Group;
import com.doitutpl.doit.R;
import com.doitutpl.doit.StaticData;

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
    EditText groupName, groupPass;
    TextView groupKey;
    Context context = this;


    // Mail----

    Session session = null;
    ProgressDialog pdialog = null;
    String rec, subject, textMessage;
    String[] users = {"chjuca@utpl.edu.ec"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agregar_grupo);

        btnCopy = findViewById(R.id.btnCopy);
        groupName = findViewById(R.id.groupName);
        groupPass = findViewById(R.id.groupPass);
        groupKey = findViewById(R.id.groupKey);
        btnGenerateKey = findViewById(R.id.btnCreate);


        btnGenerateKey.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Obtenemos los datos desde la interfaz
                String targetKeyGroup = UUID.randomUUID().toString();
                String targetKeyChat = UUID.randomUUID().toString();
                String targetPassword = groupPass.getText().toString();
                String targetNameGroup = groupName.getText().toString();

                // ! Este cosntrutor debe usarse obligatoriamente antes de llamar al método .save()
                // Utilizamos este construcor para que agregue al usuario logeado como primer miembro y como admin

                if (targetPassword.length() != 0 && targetNameGroup.length() != 0) {
                    Group group = new Group(targetKeyGroup, targetKeyChat, targetNameGroup, targetPassword, StaticData.currentUser);

                    // Guardamos el grupo en la base de datos
                    group.save(context);
                    Toast.makeText(CreateGroup.this, "!Grupo creado Exitosamente¡", Toast.LENGTH_LONG).show();
                    for(String user: users){
                        sendMail(user, targetKeyGroup);
                    }
                } else {
                    Toast.makeText(CreateGroup.this, "!Rellene todos los Campos¡", Toast.LENGTH_LONG).show();
                }

            }
        });

        btnCopy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent compartir = new Intent(android.content.Intent.ACTION_SEND);
                compartir.setType("text/plain");
                String mensaje = "Usa esta llave para unirte al grupo: *" + groupName.getText().toString() + "* en DOIT!+\n" +
                        groupKey.getText().toString() + "\n" +
                        "¡Pide la contraseña al Administrador!";
                compartir.putExtra(android.content.Intent.EXTRA_SUBJECT, "Share Key Chat");
                compartir.putExtra(android.content.Intent.EXTRA_TEXT, mensaje);
                startActivity(Intent.createChooser(compartir, "Compartir vía"));
            }
        });
    }

    public void sendMail(String receptor, String keyGroup) {
        //receptor = "chjuca@utpl.edu.ec"; // Correo al que va destinado
        subject = "Invitación de Grupo";
        textMessage = "<p>Usa esta llave para unirte al grupo: <b>" + groupName.getText().toString().toUpperCase() + "</b> en DOIT! </p>" +
                "<br>"+
                "<a href=\"https://www.doit.com/" + keyGroup + "\">Únete aquí</a>";

        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.socketFactory.port", "465");
        props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.port", "465");

        session = Session.getDefaultInstance(props, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication("junio55.jj@gmail.com", "gt7nvHmU");
            }
        });

        RetreiveFeedTask task = new RetreiveFeedTask();
        task.execute(receptor);
    }

    class RetreiveFeedTask extends AsyncTask<String, Void, String>{

        @Override
        protected String doInBackground(String... params) {

            String user = params[0];
            try{
                Message message = new MimeMessage(session);
                message.setFrom(new InternetAddress("testfrom354@gmail.com"));
                message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(user));
                message.setSubject(subject);
                message.setContent(textMessage, "text/html; charset=utf-8");
                Transport.send(message);
            } catch(MessagingException e) {
                e.printStackTrace();
            } catch(Exception e) {
                e.printStackTrace();
            }
            return null;
        }
    }
}

