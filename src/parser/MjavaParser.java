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
	private SyntaxNode if_Statement;
	private SyntaxNode while_Statement;
	private SyntaxNode print_Statement;
	private SyntaxNode varAssigan_Statement;
	private SyntaxNode arrayAssign_Statement;
	
	//表达式节点
	private SyntaxNode int_Expression;
	private SyntaxNode true_Expression;
	private SyntaxNode false_Expression;
	private SyntaxNode identifier_Expression;
	private SyntaxNode this_Expression;
	private SyntaxNode newArray_Expression;
	private SyntaxNode new_Expression;
	private SyntaxNode not_Expression;
	private SyntaxNode brace_Expression;
	
	//声明节点
	private SyntaxNode mainClass;
	private SyntaxNode classDeclaration;
	private SyntaxNode varDeclaration;
	private SyntaxNode methodDeclaration;
	
	//根节点
	private SyntaxNode goal;
	
	
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
    
    public SyntaxNode newDeclarationNode(Declaration type) {
    	SyntaxNode newNode = new SyntaxNode();
    	newNode.nodeType = NodeType.Declaration;
    	switch(type) {
    	case Goal:
    		newNode.declaration = Declaration.Goal;
    		break;
    	case MainClass:
    		newNode.declaration = Declaration.MainClass;
    		break;
    	case ClassDeclaration:
    		newNode.declaration = Declaration.ClassDeclaration;
    		break;
    	case VarDeclaration:
    		newNode.declaration = Declaration.VarDeclaration;
    		break;
    	case MethodDeclaration:
    		newNode.declaration = Declaration.MethodDeclaration;
    		break;
    	}
    	return newNode;
    }
    
    public SyntaxNode newStatementNode(Declaration type) {
    	
    	
    	return null;
    }
    
    public SyntaxNode newExpressionNode(Declaration type) {
    	
    	
    	return null;
    }
    
    private SyntaxNode goal() {
    	SyntaxNode goal_Node = new SyntaxNode();
    	goal_Node.declaration = Declaration.Goal;
    	SyntaxNode mainClassChild = new SyntaxNode();
		return goal_Node;
    }
}
