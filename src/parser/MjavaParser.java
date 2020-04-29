package parser;

import java.io.*;

import lexier.MjavaLexier;
import lexier.Token;
import lexier.Token.TokenType;
import parser.SyntaxNode;

public class MjavaParser {
	Token token;//保存当前Token
	Token preToken;
	int line = 1;
	MjavaLexier lexier;
	private FileWriter out = null;//输出信息
	
	//语句节点
	private SyntaxNode statement() {
		return null;
	}
	private SyntaxNode if_Statement() {
		return null;
	}
	private SyntaxNode while_Statement() {
		return null;
	}
	private SyntaxNode print_Statement() {
		return null;
	}
	private SyntaxNode varAssigan_Statement() {
		return null;
	}
	private SyntaxNode arrayAssign_Statement() {
		return null;
	}
	
	//表达式节点
	private SyntaxNode int_Expression() {
		return null;
	}
	private SyntaxNode true_Expression() {
		return null;
	}
	private SyntaxNode false_Expression() {
		return null;
	}
	private SyntaxNode identifier_Expression() {
		return null;
	}
	private SyntaxNode this_Expression() {
		return null;
	}
	private SyntaxNode newArray_Expression() {
		return null;
	}
	private SyntaxNode new_Expression() {
		return null;
	}
	private SyntaxNode not_Expression() {
		return null;
	}
	private SyntaxNode brace_Expression() {
		return null;
	}
	
	//声明节点
//	private SyntaxNode mainClass() {
//		return null;
//	}
//	private SyntaxNode classDeclaration() {
//		return null;
//	}
//	private SyntaxNode varDeclaration() {
//		return null;
//	}
//	private SyntaxNode methodDeclaration() {
//		return null;
//	}
	
	//根节点
//	private SyntaxNode goal;
	
	
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
		out.write("Syntax error "+message+" at line "+lexier.line+"\n");
	}
	
    /**
     * 功能：match函数完成当前Token与期望Token的匹配
     * 			  如果Token匹配则读入下一个token，反之输出unexpected token错误
     * @param expected 期望的Token
     * @throws IOException
     */
    public boolean match(TokenType expected) throws IOException {
    	if(token.getType().equals(expected)) {
    		preToken = token;
    		token = lexier.nextToken();
    		return true;
    	}else {
    		syntaxError("unexpected token: ");
    		out.write(token.getToken());
    		preToken = token;
    		token = lexier.nextToken();  
    		return false;
    	}
    }
    
    public void pushBackToken() {
    	int length = token.getToken().length();
    	for(int i = 0; i<length; i++) {
    		lexier.pushBack();
    		token = preToken;
    	}
    }
    
    public SyntaxNode newDeclarationNode(Declaration type) throws IOException {
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
    	default:
    		syntaxError("Unexpected token at line: "+line);
    		preToken = token;
    		token = lexier.nextToken();
    		break;
    	}
    	return newNode;
    }
    
//    public SyntaxNode newStatementNode() throws IOException {
////    	SyntaxNode newNode = new SyntaxNode();
////    	newNode.nodeType = NodeType.Statement;
//    	switch(token.getType()) {
//    	case KEY_WHILE:
//    		SyntaxNode newNode = while_Statement();
//    		break;
//    	case KEY_IF:
//    		SyntaxNode newNode = if_Statement();
//    		break;
//    	case KEY_PRINTLIN:
//    		newNode.statement = Statement.Print_Statement;
//    		break;
//    	case ASSIGN:
//    		newNode.statement = Statement.VarAssign_Statement;
//    		break;
//    	default:
//    		syntaxError("Unexpected token at line: "+lexier.line);
//    		preToken = token;
//    		token = lexier.nextToken();
//    		break;
//    	}
//    	
//    }
    
//    public SyntaxNode newExpressionNode(Expression type) throws IOException {
//    	SyntaxNode newNode = new SyntaxNode();
//    	newNode.nodeType = NodeType.Expression;
//    	switch(type) {
//    	case Int_Expression:
//    		newNode.expression = Expression.Int_Expression;
//    		break;
//    	case True_Expression:
//    		newNode.expression = Expression.True_Expression;
//    		break;
//    	case False_Expression:
//    		newNode.expression = Expression.False_Expression;
//    		break;
//    	case Identifier_Expression:
//    		newNode.expression = Expression.Identifier_Expression;
//    		break;
//    	case This_Expression:
//    		newNode.expression = Expression.This_Expression;
//    		break;
//    	case NewArray_Expression:
//    		newNode.expression = Expression.NewArray_Expression;
//    		break;
//    	case New_Expression:
//    		newNode.expression = Expression.New_Expression;
//    		break;
//    	case Not_Expression:
//    		newNode.expression = Expression.Not_Expression;
//    		break;
//    	case Brace_Expression:
//    		newNode.expression = Expression.Brace_Expression;
//    		break;
//    	default:
//    		syntaxError("Unexpected token at line: "+line);
//   	 	preToken = token;
//    		token = lexier.nextToken();
//    		break;
//    	}
//    	return newNode;
//    }
    
    private SyntaxNode goal() throws IOException {
    	SyntaxNode goal_Node = newDeclarationNode(Declaration.Goal);
    	SyntaxNode mainClassChild = mainClass();
    	goal_Node.childList.add(mainClassChild);
    	while(token.getType() != TokenType.EOF) {
    		SyntaxNode classDeclarationChild = classDeclaration();
    		goal_Node.childList.add(classDeclarationChild);
    	}
		return goal_Node;
    }
    
    private SyntaxNode mainClass() throws IOException {
    	SyntaxNode mainClassNode = new SyntaxNode();
    	mainClassNode.nodeType = NodeType.Declaration;
    	mainClassNode.declaration = Declaration.MainClass;
    	
    	match(TokenType.KEY_CLASS);
    	match(TokenType.IDENTIFIER);
    	match(TokenType.LBRACES);
    	match(TokenType.KEY_PUBLIC);
    	match(TokenType.KEY_STATIC);
    	match(TokenType.KEY_VOID);
    	match(TokenType.KEY_MAIN);	
    	match(TokenType.LPAREN);    	
    	match(TokenType.KEY_STRING);    	
    	match(TokenType.LBRACKET);    	
    	match(TokenType.RBRACKET);    	
    	match(TokenType.IDENTIFIER);   	
    	match(TokenType.RPAREN);   	
    	match(TokenType.LBRACES);   	
    	SyntaxNode statementChild = statement();
    	mainClassNode.childList.add(statementChild);  	
    	match(TokenType.RBRACES);    	
    	match(TokenType.RBRACES);
		return mainClassNode;
    }
    
    private SyntaxNode classDeclaration() throws IOException {
    	SyntaxNode classDeclaratioNode = new SyntaxNode();
    	classDeclaratioNode.nodeType = NodeType.Declaration;
    	classDeclaratioNode.declaration = Declaration.ClassDeclaration;
    	
    	match(TokenType.KEY_CLASS);
    	
    	match(TokenType.IDENTIFIER);
    	
    	if(match(TokenType.KEY_EXTENDS)) {
    		match(TokenType.IDENTIFIER);
    	}
    	
    	match(TokenType.LBRACES);
    	
    	while(!(token.getToken().equals("public") || token.getToken().equals("}"))) {
    		SyntaxNode varDeclarationNode = varDeclaration();
    		classDeclaratioNode.childList.add(varDeclarationNode);
    	}
    	
    	while(token.getToken().equals("public")) {
    		SyntaxNode methodDeclarationNode = methodDeclaration();
    		classDeclaratioNode.childList.add(methodDeclarationNode);
    	}
    	
    	match(TokenType.RBRACES);
    	
		return classDeclaratioNode;
	}
    
    private SyntaxNode varDeclaration() throws IOException {
    	SyntaxNode varDeclaratioNode = new SyntaxNode();
    	varDeclaratioNode.nodeType = NodeType.Declaration;
    	varDeclaratioNode.declaration = Declaration.VarDeclaration;
    	
    	if(token.getToken().equals("int")) {
    		preToken = token;
			token = lexier.nextToken();
    		if(token.getToken().equals("[")) {
    			preToken = token;
    			token = lexier.nextToken();
    			if(match(TokenType.RBRACKET)){
    				varDeclaratioNode.expType = var_Type.Int_Array;
    			}else {
    				syntaxError(" Excepted ']' ");
    				pushBackToken();//将不是]的token回退到字符流中
    			}
    		}else {//如果没有匹配到“[”，那么将当前token回退到字符流中
    			token = preToken;
    			varDeclaratioNode.expType = var_Type.Int;
    			pushBackToken();//将当前的Token回退到字符流中
    		}
    	}else if(token.getToken().equals("boolean")) {
    		preToken = token;
			token = lexier.nextToken();
    		varDeclaratioNode.expType = var_Type.Boolean;
    	}else if(token.getType() == TokenType.IDENTIFIER) {
    		preToken = token;
			token = lexier.nextToken();
    		varDeclaratioNode.expType = var_Type.Identifier;
    	}else {
    		preToken = token;
			token = lexier.nextToken();
    		syntaxError("unexpected VarType");
    	}
    	
    	match(TokenType.IDENTIFIER);
    	
		return varDeclaratioNode;
	}
    
    private boolean matchType() throws IOException {
    	if(token.getToken().equals("int")) {
    		preToken = token;
			token = lexier.nextToken();
    		if(token.getToken().equals("[")) {
    			preToken = token;
    			token = lexier.nextToken();
    			if(match(TokenType.RBRACKET)) {
    				return true;//int []
    			}else {
    				return false;
    			}
    		}else {//如果没有匹配到“[”，那么将当前token回退到字符流中
    			token = preToken;
    			pushBackToken();//将当前的Token回退到字符流中
    			return true;//int
    		}
    	}else if(token.getToken().equals("boolean")) {
    		preToken = token;
			token = lexier.nextToken();
			return true;//boolean
    	}else if(token.getType() == TokenType.IDENTIFIER) {
    		preToken = token;
			token = lexier.nextToken();
			return true;//identifier
    	}else {
    		preToken = token;
			token = lexier.nextToken();
    		syntaxError("unexpected VarType");
    		return false;
    	}
    }
    
    private SyntaxNode methodDeclaration() throws IOException {
    	SyntaxNode methodDeclaratioNode = new SyntaxNode();
    	methodDeclaratioNode.nodeType = NodeType.Declaration;
    	methodDeclaratioNode.declaration = Declaration.MethodDeclaration;
    	
    	match(TokenType.KEY_PUBLIC);
    	matchType();
    	match(TokenType.IDENTIFIER);
    	
		return methodDeclaratioNode;
	}
}
