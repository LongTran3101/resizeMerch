package ResizeMerch.ResizeMerch;

import java.awt.EventQueue;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

import javax.swing.JInternalFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.filechooser.FileNameExtensionFilter;

import org.imgscalr.Scalr;

import java.awt.BorderLayout;
import javax.swing.JLabel;
import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
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

public class ResizeMerch extends JFrame {

	/**
	 * Launch the application.
	 */
	
	public static File[] listFileChoose;
	public static JTextArea textArea = new JTextArea(20,20);
	private JTextField linkSaveFile;
	private static JTextField width;
	private static JTextField hight;
	private JTextField tileresize;
	private static Thread thread;
	private static Thread threadMain;
	private static JButton  btnNewButton = new JButton("Resize");
	static JTextArea textArea_1 = new JTextArea();
	public static void main(String[] args) {
		threadMain = new Thread(new Runnable() {

			public void run() {
				try {
					System.out.println("1");
					ResizeMerch frame = new ResizeMerch();
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
	public ResizeMerch() {
		setBounds(100, 100, 818, 507);
		getContentPane().setLayout(null);
		
		JButton btnChnnh = new JButton("Chọn ảnh");
		btnChnnh.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				JFileChooser choose=new JFileChooser();
				FileNameExtensionFilter fileNameFilter=new FileNameExtensionFilter("Hình ảnh","png");
				choose.setFileFilter(fileNameFilter);
				choose.setMultiSelectionEnabled(true);
				int x=choose.showOpenDialog(null);
				if(x==JFileChooser.APPROVE_OPTION) {
					
					File[]  files=choose.getSelectedFiles();
					listFileChoose=files;
					  //StringBuilder content = new StringBuilder();
					for (File file : files) {
						//content.append(file.getName()  + "\n");
						textArea.append(file.getName() +"\n");
						 System.out.println("You chose to open this file: " +
								 file.getAbsolutePath());
					}
					
				}
			}
		});
		btnChnnh.setBounds(0, 12, 117, 23);
		getContentPane().add(btnChnnh);

		
		
		
		JButton btnChnThMc = new JButton("Chọn thư mục lưu");
		btnChnThMc.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				JFileChooser	chooser = new JFileChooser();
		        chooser.setCurrentDirectory(new java.io.File("."));
		        chooser.setDialogTitle("Chọn thư mục lưu");
		        chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		        //
		        // disable the "All files" option.
		        //
		        chooser.setAcceptAllFileFilterUsed(false);
		        //    
		        if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {

		           String stringUrlSave = chooser.getSelectedFile().toString();
		            linkSaveFile.setText(stringUrlSave + "\\");

		        } else {
		            System.out.println("No Selection ");
		        }

			}
		});
		btnChnThMc.setBounds(0, 48, 273, 23);
		getContentPane().add(btnChnThMc);
		
		linkSaveFile = new JTextField();
		linkSaveFile.setBounds(0, 82, 332, 20);
		getContentPane().add(linkSaveFile);
		linkSaveFile.setColumns(10);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(345, 9, 433, 445);
		getContentPane().add(scrollPane);
		scrollPane.setViewportView(textArea);
		
		textArea.setEditable(false);
		
		
	
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				btnNewButton.setEnabled(false);
		        thread = new Thread(new Runnable() {

		            public void run() {
		                download();

		            }
		        });
		        thread.start();
				
				
				
			}
		});
		btnNewButton.setBounds(81, 200, 156, 60);
		getContentPane().add(btnNewButton);
		
	
		textArea_1.setBounds(24, 282, 273, 85);
		getContentPane().add(textArea_1);
		
		JLabel lblNewLabel = new JLabel("Width");
		lblNewLabel.setBounds(10, 113, 46, 14);
		getContentPane().add(lblNewLabel);
		
		width = new JTextField();
		width.setText("4500");
		width.setBounds(70, 110, 86, 20);
		getContentPane().add(width);
		width.setColumns(10);
		
		JLabel lblHeight = new JLabel("Height");
		lblHeight.setBounds(174, 116, 46, 14);
		getContentPane().add(lblHeight);
		
		hight = new JTextField();
		hight.setText("5400");
		hight.setColumns(10);
		hight.setBounds(230, 110, 86, 23);
		getContentPane().add(hight);
		
		JLabel lblTLResize = new JLabel("Tỉ lệ resize");
		lblTLResize.setBounds(10, 156, 84, 14);
		getContentPane().add(lblTLResize);
		
		tileresize = new JTextField();
		tileresize.setText("90");
		tileresize.setColumns(10);
		tileresize.setBounds(70, 153, 86, 20);
		getContentPane().add(tileresize);

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
	  
	  private  void download() {
			 try {
				 textArea_1.setText("Start!");
				 for (File file : listFileChoose) {
					 try {
						 byte[] response = Files.readAllBytes(file.toPath());
					        int newHeight = Integer.parseInt(hight.getText());
					        int newWidth = Integer.parseInt(width.getText());
					        int newHeightresize = Integer.parseInt(hight.getText()) * Integer.parseInt(tileresize.getText()) / 100;
					        int newWidthresize = Integer.parseInt(width.getText()) * Integer.parseInt(tileresize.getText()) / 100;
					        ByteArrayInputStream bais = new ByteArrayInputStream(response);
					        BufferedImage inputimage = ImageIO.read(bais);
					        BufferedImage outputImage = trimImage(inputimage);
					        Scalr.Mode mode = Scalr.Mode.FIT_TO_HEIGHT;
					        BufferedImage outputImage2 = Scalr.resize(outputImage, Scalr.Method.ULTRA_QUALITY, mode, newWidthresize - 10, newHeightresize - 10, Scalr.OP_ANTIALIAS );
					        if (outputImage2.getWidth() > newWidth) {
					          Scalr.Mode mode2 = Scalr.Mode.FIT_TO_WIDTH;
					          outputImage2 = Scalr.resize(outputImage, Scalr.Method.ULTRA_QUALITY, mode2, newWidthresize - 10, newHeightresize - 10, Scalr.OP_ANTIALIAS );
					          int i = 150;
					          int j = Math.round(((newWidth - outputImage2.getWidth()) / 2));
					          int k = 2;
					          BufferedImage bufferedImage = new BufferedImage(newWidth, newHeight, k);
					          Graphics2D graphics2D1 = bufferedImage.createGraphics();
					          RenderingHints renderingHints = new RenderingHints(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
					          renderingHints.put(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
					          renderingHints.put(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
					          graphics2D1.setRenderingHints(renderingHints);
					          graphics2D1.drawImage(outputImage2, j, i, null);
					          graphics2D1.dispose();
					          ImageIO.write(bufferedImage, "png", new File(linkSaveFile.getText() + file.getName()));
					          continue;
					        } 
					        int hightwirte = 150;
					        int widthwirte = Math.round(((newWidth - outputImage2.getWidth()) / 2));
					        int type = 2;
					        BufferedImage outputImage4 = new BufferedImage(newWidth, newHeight, type);
					        Graphics2D graphics2D = outputImage4.createGraphics();
					        RenderingHints hints = new RenderingHints(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
					        hints.put(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
					        hints.put(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
					        graphics2D.setRenderingHints(hints);
					        graphics2D.drawImage(outputImage2, widthwirte, hightwirte,null);
					        graphics2D.dispose();
					        ImageIO.write(outputImage4, "png", new File(linkSaveFile.getText() +file.getName()));
					} catch (Exception e2) {
						continue;
					}
					 
					
			       
			        
			      } 
			      //System.out.println(a.size());
				 btnNewButton.setEnabled(true);
			    } catch (Exception ex) {
			      ex.printStackTrace();
			    } 
			 btnNewButton.setEnabled(true);
			 textArea_1.setText("Done!");
		}
		
}
