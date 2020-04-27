package parser;

import java.io.*;
import lexier.MjavaLexier;
import lexier.Token;
import lexier.Token.TokenType;
import parser.SyntaxNode;

public class MjavaParser {
	Token token;//保存当前Token
	int line = 1;
	MjavaLexier lexier;
	private FileWriter out = null;//输出信息
	
	//语句节点
	static private SyntaxNode if_Statement;
	static private SyntaxNode while_Statement;
	static private SyntaxNode print_Statement;
	static private SyntaxNode varAssigan_Statement;
	static private SyntaxNode arrayAssign_Statement;
	
	
	/**
	 * @param input 输入测试文件路径
	 * @param lexierOutPut 词法分析器输出文件
	 * @param parserOutput 语法分析器输出文件
	 */
	public MjavaParser(String input, String lexierOutPut, String parserOutput) {
		lexier = new MjavaLexier(input, lexierOutPut);
		FileWriter writer= null;
		try {
			writer = new FileWriter(parserOutput);
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(-1);
		}
		out = writer;
	}
	
	/**
	 * 功能：输出错误信息到文件中
	 * @param message 错误信息
	 * @throws IOException
	 */
	private void syntaxError(String message) throws IOException {
		out.write("\n>>>");
		out.write("Syntax error "+message+" at line "+line+"\n");
	}
	
    /**
     * 功能：match函数完成当前Token与期望Token的匹配
     * 			  如果Token匹配则读入下一个token，反之输出unexpected token错误
     * @param expected 期望的Token
     * @throws IOException
     */
    public void match(TokenType expected) throws IOException {
    	if(token.getType().equals(expected)) {
    		token = lexier.nextToken();   		
    	}else {
    		syntaxError("unexpected token: ");
    		out.write(token.getToken());
    	}
    }
    
    
}
