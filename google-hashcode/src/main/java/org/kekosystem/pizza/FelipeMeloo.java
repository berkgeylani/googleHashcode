package org.kekosystem.pizza;

import java.awt.List;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.stream.Stream;

enum SECTION{
	FFF,
	SSS
}

enum SEQ{
	T,
	M,
	LX,
	LY,
	HX,
	HY
}

public class FelipeMeloo {
	
	private int R_,C_,L_,H_;
	private final char c_partition_ = '+',
					   c_slice_ = '*',
					   c_delimiter_ = ',',
					   c_section_ = '-',
					   c_end_of_sections_ = '$',
					   c_pair_delimiter_ = '/';
	
	private String pair_instance_ = "";
	private SECTION sect = SECTION.FFF;
	
	public FelipeMeloo(String filename, int R, int C, int L, int H) throws FileNotFoundException{
		
		PrintStream out = new PrintStream( new FileOutputStream(filename + ".txt") );
		System.setOut(out);
		
		R_ = R;
		C_ = C;
		L_ = L;
		H_ = H;
		
	}
	
	public void Pitbull(String resultfilename) throws IOException{
		
		System.out.println(String.join("-", new String[]{ SEQ.T.toString(), SEQ.M.toString(), SEQ.LX.toString(), SEQ.LY.toString(), SEQ.HX.toString(), SEQ.HY.toString() }));
		System.out.println();
		
		Stream<String> lines = Files.lines(Paths.get(System.getProperty("user.dir") + File.separator + resultfilename + ".txt"));
		ArrayList<String> slice_pairs_111 = new ArrayList<String>();
		ArrayList<String> slice_pairs_222 = new ArrayList<String>();
		
		lines.forEach((line)->{
					
			if(!line.isEmpty()){
				byte line_start_char = line.getBytes()[0];
				
				if( line_start_char == c_end_of_sections_ ){
					
					if(sect == SECTION.SSS)
						slice_pairs_222.add(pair_instance_);	//eval a gitmeden evvel 222 nin son elemanını da ekle.
					
					Eval(slice_pairs_111, slice_pairs_222);
					
					slice_pairs_111.clear();
					slice_pairs_222.clear();
					
					sect = SECTION.FFF;
				}
				else
				if( line_start_char == c_section_){//-111-,-222-
					
					if(Integer.parseInt(line.substring(1,line.length()-1)) == 111)
						sect = SECTION.FFF;
					else
					if(Integer.parseInt(line.substring(1,line.length()-1)) == 222){
						sect = SECTION.SSS;
						slice_pairs_111.add(pair_instance_);//son elemanı da yazdırıp sonra 222 lere geç
					}
					
					pair_instance_ = "";
				}
				else
				if(line_start_char == c_partition_){//_qqqqqq-(x,y)q_
					
					if(sect == SECTION.FFF)
						slice_pairs_111.add(pair_instance_);
					else
					if(sect == SECTION.SSS)
						slice_pairs_222.add(pair_instance_);
					
					pair_instance_ = "";
				}
				else
				if(line_start_char == c_slice_){///*T,M,x,y,X,Y
					if(pair_instance_ == "")
						pair_instance_ += line.substring(1, line.length());
					else
						pair_instance_ = String.join(Character.toString(c_pair_delimiter_), new String[]{ pair_instance_, line.substring(1, line.length())});
				}
				
			}//if line is not empty
				
			
		});//forEach
		
		lines.close();
	}//Pitbull
	
	
	@SuppressWarnings("null")
	private void Eval(ArrayList<String> l1, ArrayList<String> l2){
		
		//check L
		for(String p1 : l1){
			
			ArrayList<String> slice_collection = new ArrayList<String>();
			
			for(String p2 : l2){
				
				if(p1 != null || !p1.isEmpty()){
					String[] ar = p1.split( String.valueOf(c_pair_delimiter_) );
					for(String a : ar)
						slice_collection.add(a);
				}
				
				if(p2 != null || !p2.isEmpty()){
					String[] ar = p2.split( String.valueOf(c_pair_delimiter_) );
					for(String a : ar)
						slice_collection.add(a);
				}
				
				if(!slice_collection.isEmpty()){
					boolean isLegit = true;
					for(String slice : slice_collection){
						String[] slice_properties = slice.split( String.valueOf(c_delimiter_) );
						
						if( Integer.valueOf(slice_properties[SEQ.T.ordinal()]) < L_ || Integer.valueOf(slice_properties[SEQ.M.ordinal()]) < L_ ){
							isLegit = false;
							break;
						}
						
						int slice_size = 
								(Integer.valueOf(slice_properties[SEQ.HX.ordinal()]) - Integer.valueOf(slice_properties[SEQ.LX.ordinal()])) * (Integer.valueOf(slice_properties[SEQ.HY.ordinal()]) - Integer.valueOf(slice_properties[SEQ.LY.ordinal()]));
						
						if( slice_size > H_ ){
							isLegit = false;
							break;
						}
						
					}
					
					if(isLegit){
						for(String slice : slice_collection){
							System.out.println(slice);
						}
						System.out.println();
					}
					
					slice_collection.clear();
				}
				
			}
		}
	}
	
}
