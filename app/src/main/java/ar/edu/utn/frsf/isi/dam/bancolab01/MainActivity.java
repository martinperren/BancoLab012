package ar.edu.utn.frsf.isi.dam.bancolab01;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;
import android.widget.CheckBox;
import modelo.Cliente;
import modelo.PlazoFijo;

public class MainActivity extends AppCompatActivity {

    private PlazoFijo pf;
    private Cliente cliente;
    private EditText edtMail;
    private EditText edtCuit;
    private EditText edtMonto;
    private TextView tvDiasSeleccionados;
    private TextView tvIntereses;
    private TextView tvMensajes;
    private RadioButton optDolar;
    private RadioButton optPesos;
    private SeekBar seekDias;
    private Switch swAvisarVencimiento;
    private ToggleButton tbAcreditar;
    private CheckBox chkAceptoTerminos;
    private Button btnHacerPF;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        pf = new PlazoFijo(getResources().getStringArray(R.array.tasas));
        cliente = new Cliente();

        edtMail= (EditText) findViewById(R.id.edtMail);
        edtCuit= (EditText) findViewById(R.id.edtCuit);
        edtMonto= (EditText) findViewById(R.id.edtMonto);
        tvDiasSeleccionados= (TextView) findViewById(R.id.tvDiasSeleccionados);
        tvIntereses= (TextView) findViewById(R.id.tvIntereses);
        tvMensajes= (TextView) findViewById(R.id.tvMensajes);
        optDolar= (RadioButton) findViewById(R.id.optDolar);
        optPesos= (RadioButton) findViewById(R.id.optPesos);
        seekDias= (SeekBar) findViewById(R.id.seekDias);
        swAvisarVencimiento= (Switch) findViewById(R.id.swAvisarVencimiento);
        tbAcreditar= (ToggleButton) findViewById(R.id.tbAcreditar);
        chkAceptoTerminos= (CheckBox) findViewById(R.id.chkAceptoTerminos);
        btnHacerPF = (Button) findViewById(R.id.btnHacerPF);
        btnHacerPF.setEnabled(false);
        seekDias.setMax(170);

        seekDias.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener(){

            @Override
            public void onProgressChanged (SeekBar seekBar,int i, boolean b){
            tvDiasSeleccionados.setText(tvDiasSeleccionados.getText().subSequence(caracter(tvDiasSeleccionados.getText().toString()),tvDiasSeleccionados.getText().length()));
            tvDiasSeleccionados.setText(Integer.toString(i+10)+tvDiasSeleccionados.getText());
            pf.setDias(i+10);
                if(!edtMonto.getText().toString().isEmpty()) {
                    pf.setMonto(Double.parseDouble(edtMonto.getText().toString()));
                    tvIntereses.setText(tvIntereses.getText().subSequence(0, 1));
                    tvIntereses.setText(tvIntereses.getText().toString() + pf.intereses().toString());
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });

        chkAceptoTerminos.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
            if(compoundButton.isChecked()){
                btnHacerPF.setEnabled(true);
            }else{
                Context context = getApplicationContext();
                CharSequence text = "Es obligatorio aceptar las condiciones";
                int duration = Toast.LENGTH_SHORT;
                Toast toast = Toast.makeText(context, text, duration);
                toast.show();
                btnHacerPF.setEnabled(false);
            }
            }
        });



//////////////////////////////////////////////////////////////////////

        swAvisarVencimiento.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                       pf.setAvisarVencimiento(true);
                } else {
                    pf.setAvisarVencimiento(false);
                }
            }
        });

        tbAcreditar.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener()  {

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    pf.setRenovarAutomaticamente(true);
                } else {
                    pf.setRenovarAutomaticamente(false);
                }
            }
        });


/////////////////////////////////////////////////////////////////////


        btnHacerPF.setOnClickListener(new Button.OnClickListener() {

            @Override
            public void onClick(View v) {
                tvMensajes.setText("");
                if(!edtMail.getText().toString().matches("") && !edtCuit.getText().toString().matches("") && !(Integer.parseInt(edtCuit.getText().toString())<=0) && !(seekDias.getProgress()<10)){
                    tvMensajes.setTextColor(Color.BLUE);
                    tvMensajes.setText("El plazo fijo se realizo exitosamente\nPlazoFijo{dias="+
                    pf.getDias()+", monto="+pf.getMonto()+",\navisarVencimiento="+pf.getAvisarVencimiento()+
                    ", renovarAutomaticamente="+pf.getRenovarAutomaticamente()+",\nmoneda="+pf.getMoneda()+"}");
                }else{
                    tvMensajes.setTextColor(Color.RED);
                    if (edtMail.getText().toString().isEmpty()) {
                        Context context = getApplicationContext();
                        CharSequence text = "Error";
                        int duration = Toast.LENGTH_SHORT;
                        Toast toast = Toast.makeText(context, text, duration);
                        toast.show();
                        tvMensajes.setText("El mail no debe ser vacío\n");
                    }
                    if (edtCuit.getText().toString().isEmpty()) {
                        Context context = getApplicationContext();
                        CharSequence text = "Error";
                        int duration = Toast.LENGTH_SHORT;
                        Toast toast = Toast.makeText(context, text, duration);
                        toast.show();
                        tvMensajes.setText(tvMensajes.getText().toString() + "El cuit/cuil no debe ser vacio\n");
                    }
                    if (Integer.parseInt(edtMonto.getText().toString()) <= 0) {
                        Context context = getApplicationContext();
                        CharSequence text = "Error";
                        int duration = Toast.LENGTH_SHORT;
                        Toast toast = Toast.makeText(context, text, duration);
                        toast.show();
                        tvMensajes.setText(tvMensajes.getText().toString() + "El monto debe ser mayor que 0\n");
                    }
                    if (seekDias.getProgress() < 10) {
                        Context context = getApplicationContext();
                        CharSequence text = "Error";
                        int duration = Toast.LENGTH_SHORT;
                        Toast toast = Toast.makeText(context, text, duration);
                        toast.show();
                        tvMensajes.setText(tvMensajes.getText().toString() + "La cantidad de dias de plazo debe ser mayor que 10");
                    }
                }
            }
        });
    }

    public int caracter(String dias){
        int i=0;
        while(Character.isDigit(tvDiasSeleccionados.getText().charAt(i))){
            i++;
        }
        return i;
    }

}
