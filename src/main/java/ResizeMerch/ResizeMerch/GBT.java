package ResizeMerch.ResizeMerch;

import java.awt.EventQueue;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

import javax.swing.JInternalFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.filechooser.FileNameExtensionFilter;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.imgscalr.Scalr;
import org.json.JSONArray;
import org.json.JSONObject;

import java.awt.BorderLayout;
import javax.swing.JLabel;
import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.awt.event.ActionEvent;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.WindowConstants;

public class GBT extends JFrame {

	/**
	 * Launch the application.
	 */
	
	public static File[] listFileChoose;
	public static JTextArea textArea = new JTextArea(20,20);
	public static JTextArea message = new JTextArea();
	private static Thread thread;
	private static Thread threadMain;
	private JTextField model;
	private JTextField temperature;
	private static JTextField key;
	public static void main(String[] args) {
		threadMain = new Thread(new Runnable() {

			public void run() {
				try {
					System.out.println("1");
					GBT frame = new GBT();
					frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
					frame.addWindowListener(new WindowAdapter() {
	                    @Override
	                    public void windowClosing(WindowEvent e) {
	                        System.out.println("WindowClosingDemo.windowClosing");
	                     
	                        if (thread != null) {
	                            try {
	                            	thread.interrupt();
	                            	//thread.suspend();;
	                            } catch (Exception er) {
	                               
	                            }
	                           
	                        } 
//	                        if (threadMain != null) {
//	                            try {
//	                            	threadMain.interrupt();
//	                            	//thread.suspend();;
//	                            } catch (Exception er) {
//	                               
//	                            }
//	                           
//	                        } 

	                    }

	                    @Override
	                    public void windowOpened(WindowEvent e) {

	                       
	                    }
	                });
					
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
	        });
		threadMain.start();
	
	}
	
	
	

	/**
	 * Create the frame.
	 */
	public GBT() {
		setBounds(100, 100, 818, 507);
		getContentPane().setLayout(null);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(345, 9, 433, 445);
		getContentPane().add(scrollPane);
		scrollPane.setViewportView(textArea);
		
		textArea.setEditable(false);
		
		model = new JTextField();
		model.setText("gpt-3.5-turbo");
		model.setBounds(90, 21, 247, 32);
		getContentPane().add(model);
		model.setColumns(10);
		
		temperature = new JTextField();
		temperature.setText("0.7");
		temperature.setBounds(205, 71, 132, 32);
		getContentPane().add(temperature);
		temperature.setColumns(10);
		
		JLabel lblNewLabel_1 = new JLabel("model");
		lblNewLabel_1.setBounds(21, 24, 92, 26);
		getContentPane().add(lblNewLabel_1);
		
		JLabel lblNewLabel_1_1 = new JLabel("temperature");
		lblNewLabel_1_1.setBounds(21, 74, 165, 26);
		getContentPane().add(lblNewLabel_1_1);
		
		JLabel lblNewLabel_1_1_1 = new JLabel("message");
		lblNewLabel_1_1_1.setBounds(79, 163, 165, 26);
		getContentPane().add(lblNewLabel_1_1_1);
		
		JButton btnNewButton = new JButton("genarate");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					
					String content="{\"model\": \"gpt-3.5-turbo\",\"messages\": [{\"role\": \"user\",\"content\":\""+ message.getText()+"\"}],\"temperature\": 0.7}";
//					 JSONObject jsonObject = new JSONObject(content);
					 System.out.println(content);
//					System.out.println(content);
					CloseableHttpClient client = HttpClients.createDefault();
					  HttpPost httpPost = new HttpPost("https://api.openai.com/v1/chat/completions");
					  StringEntity entity = new StringEntity(content);
					  httpPost.setEntity(new StringEntity(content, ContentType.TEXT_PLAIN.withCharset(StandardCharsets.UTF_8)));
					  httpPost.setHeader("Accept", "application/json");
					  httpPost.setHeader("Content-type", "application/json");
					  httpPost.setHeader("Authorization", "Bearer "+key.getText());
					  CloseableHttpResponse response = client.execute(httpPost);
					  String responseBody = EntityUtils.toString(response.getEntity(), StandardCharsets.UTF_8);
					  JSONObject jsonObject = new JSONObject(responseBody);
					  
					  //String contentGBT = jsonObject.getString("choices");
					  org.json.JSONArray contentGBT = jsonObject.getJSONArray("choices");
					  System.out.println(contentGBT);
					  JSONObject  acbcbc= contentGBT.getJSONObject(0);
					  JSONObject mesage=acbcbc.getJSONObject("message");
					  String text= mesage.getString("content");
				        System.out.println(text);
				        textArea.setText(text);
					  client.close();
				} catch (Exception e) {
					e.printStackTrace();
					// TODO: handle exception
				}
				
					
			}
		});
		btnNewButton.setBounds(79, 380, 141, 35);
		getContentPane().add(btnNewButton);
		
		key = new JTextField();
		key.setText("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");
		key.setColumns(10);
		key.setBounds(79, 108, 258, 32);
		getContentPane().add(key);
		
		JLabel lblNewLabel_1_2 = new JLabel("KEY");
		lblNewLabel_1_2.setBounds(21, 111, 70, 26);
		getContentPane().add(lblNewLabel_1_2);
		
		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(21, 203, 303, 156);
		getContentPane().add(scrollPane_1);
		scrollPane_1.setViewportView(message
				);

	}
	
	 
	  private static BufferedImage trimImage(BufferedImage image) {
	    try {
	      WritableRaster raster = image.getAlphaRaster();
	      int width = raster.getWidth();
	      int height = raster.getHeight();
	      int left = 0;
	      int top = 0;
	      int right = width - 1;
	      int bottom = height - 1;
	      int minRight = width - 1;
	      int minBottom = height - 1;
	      label53: for (; top < bottom; top++) {
	        for (int x = 0; x < width; x++) {
	          if (raster.getSample(x, top, 0) != 0) {
	            minRight = x;
	            minBottom = top;
	            break label53;
	          } 
	        } 
	      } 
	      label54: for (; left < minRight; left++) {
	        for (int y = height - 1; y > top; y--) {
	          if (raster.getSample(left, y, 0) != 0) {
	            minBottom = y;
	            break label54;
	          } 
	        } 
	      } 
	      label55: for (; bottom > minBottom; bottom--) {
	        for (int x = width - 1; x >= left; x--) {
	          if (raster.getSample(x, bottom, 0) != 0) {
	            minRight = x;
	            break label55;
	          } 
	        } 
	      } 
	      label52: for (; right > minRight; right--) {
	        for (int y = bottom; y >= top; y--) {
	          if (raster.getSample(right, y, 0) != 0)
	            break label52; 
	        } 
	      } 
	      return image.getSubimage(left, top, right - left + 1, bottom - top + 1);
	    } catch (Exception e) {
	      return image;
	    } 
	  }
}
