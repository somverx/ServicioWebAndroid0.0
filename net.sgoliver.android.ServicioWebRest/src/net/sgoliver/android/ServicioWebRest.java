package net.sgoliver.android;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

public class ServicioWebRest extends Activity {
	
	private Button btnInsertar;
	private Button btnActualizar;
	private Button btnEliminar;
	private Button btnObtener;
	private Button btnListar;
	
	private EditText txtId;
	private EditText txtNombre;
	private EditText txtTelefono;
	
	private TextView lblResultado;
	private ListView lstClientes;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        btnInsertar = (Button)findViewById(R.id.btnInsertar);
        btnActualizar = (Button)findViewById(R.id.btnActualizar);
        btnEliminar = (Button)findViewById(R.id.btnEliminar);
        btnObtener = (Button)findViewById(R.id.btnObtener);
        btnListar = (Button)findViewById(R.id.btnListar);
        
        txtId = (EditText)findViewById(R.id.txtId);
        txtNombre = (EditText)findViewById(R.id.txtNombre);
        txtTelefono = (EditText)findViewById(R.id.txtTelefono);
        
        lblResultado = (TextView)findViewById(R.id.lblResultado);
        lstClientes = (ListView)findViewById(R.id.lstClientes);
        
        btnInsertar.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				HttpClient httpClient = new DefaultHttpClient();
							        
				HttpPost post = new HttpPost("http://10.0.2.2:2731/Api/Clientes/Cliente");
				post.setHeader("content-type", "application/json");
				
				try
		        {
					//Construimos el objeto cliente en formato JSON
					JSONObject dato = new JSONObject();
					
					//dato.put("Id", Integer.parseInt(txtId.getText().toString()));
					dato.put("Nombre", txtNombre.getText().toString());
					dato.put("Telefono", Integer.parseInt(txtTelefono.getText().toString()));
					
					StringEntity entity = new StringEntity(dato.toString());
					post.setEntity(entity);
					
		        	HttpResponse resp = httpClient.execute(post);
		        	String respStr = EntityUtils.toString(resp.getEntity());
		        	
		        	if(respStr.equals("true"))
		        		lblResultado.setText("Insertado OK.");
		        }
		        catch(Exception ex)
		        {
		        	Log.e("ServicioRest","Error!", ex);
		        }
			}
		});
        
        btnActualizar.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				HttpClient httpClient = new DefaultHttpClient();
							        
				HttpPut put = new HttpPut("http://10.0.2.2:2731/Api/Clientes/Cliente");
				put.setHeader("content-type", "application/json");
				
				try
		        {
					//Construimos el objeto cliente en formato JSON
					JSONObject dato = new JSONObject();
					//Objeto
					dato.put("Id", Integer.parseInt(txtId.getText().toString()));
					dato.put("Nombre", txtNombre.getText().toString());
					dato.put("Telefono", Integer.parseInt(txtTelefono.getText().toString()));
					
					StringEntity entity = new StringEntity(dato.toString());
					put.setEntity(entity);
					
		        	HttpResponse resp = httpClient.execute(put);
		        	String respStr = EntityUtils.toString(resp.getEntity());
		        	
		        	if(respStr.equals("true"))
		        		lblResultado.setText("Actualizado OK.");
		        }
		        catch(Exception ex)
		        {
		        	Log.e("ServicioRest","Error!", ex);
		        }
			}
		});
        
        btnEliminar.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				HttpClient httpClient = new DefaultHttpClient();
							        
				String id = txtId.getText().toString();
				
				HttpDelete del = 
						new HttpDelete("http://10.0.2.2:2731/Api/Clientes/Cliente/" + id);
				
				del.setHeader("content-type", "application/json");
				
				try
		        {			
		        	HttpResponse resp = httpClient.execute(del);
		        	String respStr = EntityUtils.toString(resp.getEntity());
		        	
		        	if(respStr.equals("true"))
		        		lblResultado.setText("Eliminado OK.");
		        }
		        catch(Exception ex)
		        {
		        	Log.e("ServicioRest","Error!", ex);
		        }
			}
		});
        
        btnObtener.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				HttpClient httpClient = new DefaultHttpClient();
							        
				String id = txtId.getText().toString();
				
				HttpGet del = 
						new HttpGet("http://10.0.2.2:2731/Api/Clientes/Cliente/" + id);
				
				del.setHeader("content-type", "application/json");
				
				try
		        {			
		        	HttpResponse resp = httpClient.execute(del);
		        	String respStr = EntityUtils.toString(resp.getEntity());
		        	
		        	JSONObject respJSON = new JSONObject(respStr);
		        	
		        	int idCli = respJSON.getInt("Id");
		        	String nombCli = respJSON.getString("Nombre");
		        	int telefCli = respJSON.getInt("Telefono");
		        	
		        	lblResultado.setText("" + idCli + "-" + nombCli + "-" + telefCli);
		        }
		        catch(Exception ex)
		        {
		        	Log.e("ServicioRest","Error!", ex);
		        }
			}
		});
        
        btnListar.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				HttpClient httpClient = new DefaultHttpClient();
				
				HttpGet del = 
						new HttpGet("http://10.0.2.2:2731/Api/Clientes");
				
				del.setHeader("content-type", "application/json");
				
				try
		        {			
		        	HttpResponse resp = httpClient.execute(del);
		        	String respStr = EntityUtils.toString(resp.getEntity());
		        	
		        	JSONArray respJSON = new JSONArray(respStr);
		        	
		        	String[] clientes = new String[respJSON.length()];
		        			
		        	for(int i=0; i<respJSON.length(); i++)
		        	{
		        		JSONObject obj = respJSON.getJSONObject(i);
		        		
			        	int idCli = obj.getInt("Id");
			        	String nombCli = obj.getString("Nombre");
			        	int telefCli = obj.getInt("Telefono");
			        	
		        		clientes[i] = "" + idCli + "-" + nombCli + "-" + telefCli;
		        	}
		        	
		        	//Rellenamos la lista con los resultados
		        	ArrayAdapter<String> adaptador =
		        		    new ArrayAdapter<String>(ServicioWebRest.this,
		        		        android.R.layout.simple_list_item_1, clientes);
		        		 
		        	lstClientes.setAdapter(adaptador);
		        }
		        catch(Exception ex)
		        {
		        	Log.e("ServicioRest","Error!", ex);
		        }
			}
		});
    }
}