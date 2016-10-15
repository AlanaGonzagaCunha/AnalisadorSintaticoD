package exceptions;

public class LexicalParseException extends Exception{
	private static final long serialVersionUID = 1L;
	
	public static String s(String lineBuffer){
		String erro= "";
		for (int j = 0; j < lineBuffer.length(); j++) {
			erro+=" ";
		}
		erro+="^";
		return erro;
	}
	
	public LexicalParseException(int lineCount,String lineBuffer,char c) {
		super("[ERR0] line " + lineCount + "Token invalido: '" + c + "' or ASCII "+(int)c
				+ "\n\t"+ lineBuffer+c+"\n\t"+s(lineBuffer)+" Token invalido");
		
	}

	
	
}
