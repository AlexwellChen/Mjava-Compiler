package lexier;

import java.io.*;
import lexier.Token;
import lexier.Token.TokenType;

/**
 * @author Alexwell
 *
 */
public class MjavaLexier {

	private PushbackReader in = null;
	/*
	 * 使用PushbackReader的原因： 在判断identifier的时候，依据最长匹配原则 我们遇到符号时可以终止，但是此时已经将符号读入
	 * 使用推回将符号返回到文件流中，下一次再处理 这个时候我们可以处理当前这个单词是identifier还是保留关键字
	 */
	private FileWriter out = null;//输出信息
	
	private int state = 0; // 当前状态

	private int start = 0; // 起始状态

	private char ch; // 输入字符

	private StringBuffer buffer = new StringBuffer(); // 缓存当前的字符串用于判断是否是关键字

	public int line = 1; // 行号，文件起始位置是第一行

	private int column = 0; // 列号，文件起始位置是第一列

	/**
	 * @param inputFile 输入文件名
	 */
	public MjavaLexier(String inputFile, String outputFile) {
		PushbackReader reader = null;
		FileWriter writer= null;
		try {
			reader = new PushbackReader(new FileReader(inputFile),10);
			writer = new FileWriter(outputFile);
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(-1);
		}
		in = reader;
		out = writer;
	}

	/**
	 * 将一个字符读入buffer中，进入DFA判断
	 */
	private void getNext() {
		try {
			ch = (char) in.read();
			buffer.append(ch);
			column++;
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(-1);
		}
	}

	/**
	 * 将刚刚读入的字符回退到输入流当中
	 */
	private void pushBack() {
		try {
			in.unread(buffer.charAt(buffer.length() - 1)); // 将最后一个字符回退到流中
			buffer.deleteCharAt(buffer.length() - 1);
			column--;
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(-1);
		}
	}

	/**
	 * @param type
	 * @return new Token
	 */
	public Token getToken(TokenType type) {
		state = 0;
		start = 0;
		String token = buffer.toString();
		buffer.setLength(0);
		return new Token(type, token, line, column);
	}

	/**
	 * 清空buffer中的内容
	 */
	private void dropChar() {
		buffer.setLength(0);
	}

	public Token nextToken() throws IOException {

		while (true) {
			switch (state) {
			
			case 0:
				// 得到下一个字符
				getNext();
				// 判断是否是空白
				if (Character.isWhitespace(ch)) {
					if (ch == '\n') {
						line++;
						column = 0;
					}
					dropChar();
					break;
				}else if (Character.isDigit(ch)) {
					state = 1;		//进入数字
				}else if(Character.isLetter(ch)) {
					state = 2;		//进入字符
				}else {
					state = 4;		//进入符号
				}
				break;
			//数字
			case 1:
				// 得到下一个字符
				getNext();
				if (Character.isDigit(ch)) {
					state = 1;
				}else {
					if(ch == '.') {
						getNext();
						while(Character.isDigit(ch)) {
							getNext();
						}
						out.write("get nextToken error! \n");
						pushBack();
						return getToken(TokenType.ILLEGAL_TOKEN);
					}
					if(ch == ';') {
						pushBack();
						return getToken(TokenType.INTEGERLITERAL);
					}
					// 判断是否是空白
					if (Character.isWhitespace(ch)) {
						if (ch == '\n') {
							line++;
							column = 1;
						}
						return getToken(TokenType.INTEGERLITERAL);
					}
						if((!Character.isLetter(ch))) {
							pushBack();
							return getToken(TokenType.INTEGERLITERAL);
						}
						out.write("get nextToken error! \n");
						while(Character.isLetter(ch)) {
							getNext();
						}
						return getToken(TokenType.ILLEGAL_TOKEN);	
				}
				break;
			//字符	
			case 2:
				// 得到下一个字符
				getNext();
				if(Character.isLetterOrDigit(ch)) {
					state = 2;
				}else if(ch == '.') {
					String temp = buffer.toString();
					if(temp.equals("System.") || temp.equals("System.out.")) {
						state = 2;
					}else {
						pushBack();
						return getToken(TokenType.IDENTIFIER);
					}
				}else if(ch == '_'){
					state = 3;
				}else {
					pushBack();
					String key = buffer.toString();
					if(key.equals("class")) {
						return getToken(TokenType.KEY_CLASS);
					}else if(key.equals("public")) {
						return getToken(TokenType.KEY_PUBLIC);
					}else if(key.equals("static")) {
						return getToken(TokenType.KEY_STATIC);
					}else if(key.equals("void")) {
						return getToken(TokenType.KEY_VOID);
					}else if(key.equals("main")) {
						return getToken(TokenType.KEY_MAIN);
					}else if(key.equals("string")) {
						return getToken(TokenType.KEY_STRING);
					}else if(key.equals("extends")) {
						return getToken(TokenType.KEY_EXTENDS);
					}else if(key.equals("return")) {
						return getToken(TokenType.KEY_RETURN);
					}else if(key.equals("int")) {
						return getToken(TokenType.KEY_INT);
					}else if(key.equals("boolean")) {
						return getToken(TokenType.KEY_BOOLEAN);
					}else if(key.equals("if")) {
						return getToken(TokenType.KEY_IF);
					}else if(key.equals("else")) {
						return getToken(TokenType.KEY_ELSE);
					}else if(key.equals("while")) {
						return getToken(TokenType.KEY_WHILE);
					}else if(key.equals("System.out.println")) {
						return getToken(TokenType.KEY_PRINTLIN);
					}else if(key.equals("length")) {
						return getToken(TokenType.KEY_LENGTH);
					}else if(key.equals("true")) {
						return getToken(TokenType.KEY_TRUE);
					}else if(key.equals("false")) {
						return getToken(TokenType.KEY_FALSE);
					}else if(key.equals("this")) {
						return getToken(TokenType.KEY_THIS);
					}else if(key.equals("new")) {
						return getToken(TokenType.KEY_NEW);
					}else {
						return getToken(TokenType.IDENTIFIER);
					}
				}
				break;
				
			case 3:
				getNext();
				if(Character.isLetterOrDigit(ch)) {
					state = 2;
				}else {
					pushBack();
					out.write("get nextToken error! ");
					out.write("Find illegal character "+ch);
					out.write(" At line "+line+", column "+column+"\n");
				}
				break;
				
			case 4:
				if (ch == '+') {
					return getToken(TokenType.PLUS);
				}else if (ch == '-') {
					return getToken(TokenType.MINUS);
				}else if (ch == '*') {
					return getToken(TokenType.TIMES);
				}else if (ch == '(') {
					return getToken(TokenType.LPAREN);
				}else if (ch == ')') {
					return getToken(TokenType.RPAREN);
				}else if (ch == '{') {
					return getToken(TokenType.LBRACES);
				}else if (ch == '}') {
					return getToken(TokenType.RBRACES);
				}else if (ch == '[') {
					return getToken(TokenType.LBRACKET);
				}else if (ch == ']') {
					return getToken(TokenType.RBRACKET);
				}else if (ch == ';') {
					return getToken(TokenType.SEMICOLON);
				}else if (ch == '<') {
					return getToken(TokenType.LESS);
				}
				else if (ch == '!') {
					return getToken(TokenType.LOGICAL_NOT);
				}
				else if (ch == '&') {
					getNext();
					if(ch == '&'){
						return getToken(TokenType.LOGICAL_AND);
					}else {
						pushBack();
						out.write("get nextToken error! ");
						out.write("Find illegal character &");
						out.write(" At line "+line+"\n");
						return getToken(TokenType.ILLEGAL_TOKEN);
					}
				}
				else if(ch == '.') {
					getNext();
					if(Character.isWhitespace(ch)||Character.isLetter(ch)) {
						pushBack();
						return getToken(TokenType.POINT);
					}
					else {
						out.write("get nextToken error! ");
						out.write("Find illegal character "+'.');
						out.write(" At line "+line+"\n");
						while(!Character.isWhitespace(ch)) {
							getNext();
						}
						pushBack();
						return getToken(TokenType.ILLEGAL_TOKEN);
					}
					
				}
				else if (ch == '=') {
					return getToken(TokenType.ASSIGN);
				}
				else if (ch == ',') {
					return getToken(TokenType.COMMA);
				}
				else if ((ch&0xff) == 0xff) {//文件结尾，返回EOF
					return getToken(TokenType.EOF);
				}
				else {
					out.write("get nextToken error! ");
					out.write("Find illegal character "+ch);
					out.write(" At line "+line+", column "+column+"\n");
					while(Character.isLetter(ch)||ch=='_'){
						getNext();
						if(!(Character.isLetter(ch)||ch=='_')) {
							pushBack();
							break;
						}
					}
					return getToken(TokenType.ILLEGAL_TOKEN);
				}
				
			default:
				out.write("Get nextToken error! ");
				out.write("Find illegal state:"+state+"\n");
				System.exit(1);

			}
			
		}
	}
	
	/**
	 * @param inputFile	输入文件路径
	 * @param outputFile	输出文件路径
	 */
	public static void start(String inputFile, String outputFile) {
		try {
			MjavaLexier lexier = new MjavaLexier(inputFile, outputFile);
			Token token = lexier.nextToken();
			while(token.getType() != TokenType.EOF) {
				lexier.out.write(token.getToken()+"\t\t\t"+token.getType()+"\n");
				token = lexier.nextToken();
			}
			lexier.out.close();
		} catch (IOException e){
			e.printStackTrace();
		}
		
	}

	/**
	 * @param args
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException {
		MjavaLexier.start("E:\\Users\\Alexwell\\eclipse_workspace\\Mjava\\src\\testFile\\test1.txt", "C:\\Users\\Alexwell\\Desktop\\myTestOut.txt");
	}

}
