package parser;

import java.io.*;
import java.util.ArrayList;

import lexier.MjavaLexier;
import lexier.Token;
import lexier.Token.TokenType;
import parser.SyntaxNode;

public class MjavaParser {
	Token token;//保存当前Token
	Token preToken;//前一个Token
	Token ppreToken;//前两个Token
	Token pppreToken;//前三个Token
	public ArrayList<TokenType> Exp_FirstSet = new ArrayList<TokenType>() {{
		add(TokenType.INTEGERLITERAL);
		add(TokenType.KEY_TRUE);
		add(TokenType.KEY_FALSE);
		add(TokenType.IDENTIFIER);
		add(TokenType.KEY_THIS);
		add(TokenType.KEY_NEW);
		add(TokenType.LOGICAL_NOT);
		add(TokenType.LPAREN);
	}};
	int line = 1;
	MjavaLexier lexier;
	private FileWriter out = null;//输出信息
	
	//语句节点
//	private SyntaxNode statement() {
//		return null;
//	}
//	private SyntaxNode if_Statement() {
//		return null;
//	}
//	private SyntaxNode while_Statement() {
//		return null;
//	}
//	private SyntaxNode print_Statement() {
//		return null;
//	}
//	private SyntaxNode varAssigan_Statement() {
//		return null;
//	}
//	private SyntaxNode arrayAssign_Statement() {
//		return null;
//	}
	//表达式节点
//	private SyntaxNode expression() {
//		return null;
//	}
//	private SyntaxNode int_Expression() {
//		return null;
//	}
//	private SyntaxNode true_Expression() {
//		return null;
//	}
//	private SyntaxNode false_Expression() {
//		return null;
//	}
//	private SyntaxNode identifier_Expression() {
//		return null;
//	}
//	private SyntaxNode this_Expression() {
//		return null;
//	}
//	private SyntaxNode newArray_Expression() {
//		return null;
//	}
//	private SyntaxNode new_Expression() {
//		return null;
//	}
//	private SyntaxNode not_Expression() {
//		return null;
//	}
//	private SyntaxNode brace_Expression() {
//		return null;
//	}
	
	//额外的语句节点
//	private SyntaxNode a_Statement() {
//		return null;
//	}
//	private SyntaxNode op_A() {
//		return null;
//	}
//	private SyntaxNode exp_A() {
//		return null;
//	}
//	private SyntaxNode length_A() {
//		return null;
//	}
//	private SyntaxNode method_A() {
//		return null;
//	}
//	private SyntaxNode null_A() {
//		return null;
//	}
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
	
	//工具函数定义部分
	
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
    		getNextToken();
    		return true;
    	}else {
    		syntaxError("unexpected token: ");
    		out.write(token.getToken());
    		getNextToken();
    		return false;
    	}
    }
    
    /**
     * 回退当前token到字符流中
     */
    public void pushBackToken() {
    	lexier.pushBackWhitSpace();
    	int length = token.getToken().length();
    	for(int i = 0; i<length; i++) {
    		lexier.pushBack();
    	}
    	token = preToken;
		preToken = ppreToken;
		ppreToken = pppreToken;
    }
    
    public void getNextToken() throws IOException {
    	pppreToken = ppreToken;
    	ppreToken = preToken;
    	preToken = token;
    	token = lexier.nextToken();
    }
    
    /**
     * 判断当前token是否是定义变量的type
     * @return
     * @throws IOException
     */
    private boolean matchType() throws IOException {
    	if(token.getToken().equals("int")) {
    		getNextToken();
    		if(token.getToken().equals("[")) {
    			getNextToken();
    			if(match(TokenType.RBRACKET)) {
    				return true;//int []
    			}else {
    				return false;
    			}
    		}else {//如果没有匹配到“[”，那么将当前token回退到字符流中
    			pushBackToken();//将当前的Token回退到字符流中
    			return true;//int
    		}
    	}else if(token.getToken().equals("boolean")) {
    		getNextToken();
			return true;//boolean
    	}else if(token.getType() == TokenType.IDENTIFIER) {
    		getNextToken();
			return true;//identifier
    	}else {
    		getNextToken();
    		syntaxError("unexpected type");
    		return false;
    	}
    }
    
    /*
     * 递归下降函数定义部分
     */
    
    
//    public SyntaxNode newDeclarationNode(Declaration type) throws IOException {
//    	SyntaxNode newNode = new SyntaxNode();
//    	newNode.nodeType = NodeType.Declaration;
//    	switch(type) {
//    	case Goal:
//    		newNode.declaration = Declaration.Goal;
//    		break;
//    	case MainClass:
//    		newNode.declaration = Declaration.MainClass;
//    		break;
//    	case ClassDeclaration:
//    		newNode.declaration = Declaration.ClassDeclaration;
//    		break;
//    	case VarDeclaration:
//    		newNode.declaration = Declaration.VarDeclaration;
//    		break;
//    	case MethodDeclaration:
//    		newNode.declaration = Declaration.MethodDeclaration;
//    		break;
//    	default:
//    		syntaxError("Unexpected token at line: "+line);
//    		preToken = token;
//    		token = lexier.nextToken();
//    		break;
//    	}
//    	return newNode;
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
    	SyntaxNode goal_Node = new SyntaxNode();
    	goal_Node.declaration = Declaration.Goal;
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
    		getNextToken();
    		if(token.getToken().equals("[")) {
    			getNextToken();
    			if(match(TokenType.RBRACKET)){
    				varDeclaratioNode.expType = var_Type.Int_Array;
    			}else {
    				syntaxError(" Excepte ']' ");
    				pushBackToken();//将不是]的token回退到字符流中
    			}
    		}else {//如果没有匹配到“[”，那么将当前token回退到字符流中
    			varDeclaratioNode.expType = var_Type.Int;
    			pushBackToken();//将当前的Token回退到字符流中
    		}
    	}else if(token.getToken().equals("boolean")) {
    		getNextToken();
    		varDeclaratioNode.expType = var_Type.Boolean;
    	}else if(token.getType() == TokenType.IDENTIFIER) {
    		getNextToken();
    		varDeclaratioNode.expType = var_Type.Identifier;
    	}else {
    		getNextToken();
    		syntaxError("unexpected VarType");
    	}
    	
    	match(TokenType.IDENTIFIER);
    	match(TokenType.SEMICOLON);
		return varDeclaratioNode;
	}
    
    private SyntaxNode methodDeclaration() throws IOException {
    	SyntaxNode methodDeclaratioNode = new SyntaxNode();
    	methodDeclaratioNode.nodeType = NodeType.Declaration;
    	methodDeclaratioNode.declaration = Declaration.MethodDeclaration;
    	
    	match(TokenType.KEY_PUBLIC);
    	matchType();
    	match(TokenType.IDENTIFIER);
    	match(TokenType.LPAREN);
    	if(matchType()) {
    		match(TokenType.IDENTIFIER);
    		while(match(TokenType.COMMA)) {
    			matchType();
    			match(TokenType.IDENTIFIER);
    		}
    	}
    	match(TokenType.RPAREN);
    	match(TokenType.LBRACES);
    	while(matchType()) {
    		pushBackToken();//将type的token回退，便于创建VarDeclaration节点
    		SyntaxNode varDeclarationChildNode = varDeclaration();
    		methodDeclaratioNode.childList.add(varDeclarationChildNode);
    	}
		return methodDeclaratioNode;
	}
    
    private SyntaxNode statement() throws IOException {
    	SyntaxNode newNode = null;
    	switch(token.getType()) {
    	case LBRACES:
    		newNode = statementSeries();
    		match(TokenType.RBRACES);
    		return newNode;
    	case KEY_WHILE:
    		newNode = while_Statement();
    		return newNode;
    	case KEY_IF:
    		newNode = if_Statement();
    		return newNode;
    	case KEY_PRINTLIN:
    		newNode = print_Statement();
    		return newNode;
    	case ASSIGN:
    		newNode = assigan_Statement();
    		return newNode;
    	default:
    		syntaxError("Unexpected token at line: "+lexier.line);
    		getNextToken();
    		return null;
    	}
    }
    
	private SyntaxNode statementSeries() throws IOException {
		SyntaxNode newNode = new SyntaxNode();
		newNode.nodeType = NodeType.Statement;
		newNode.statement = Statement.Series_Statement;
		
		match(TokenType.LBRACES); 
		while(!token.getToken().equals("}")) {//由于}与seriesNode无关，所以将match放在statement()中
			SyntaxNode childNode = statement();
			if(childNode != null) {
				newNode.childList.add(childNode);
			}
		}
		return newNode;
	}
	
	private SyntaxNode if_Statement() throws IOException {
		SyntaxNode newNode = new SyntaxNode();
		newNode.nodeType = NodeType.Statement;
		newNode.statement = Statement.If_Statement;
		
		match(TokenType.KEY_IF);
		match(TokenType.LPAREN);
		SyntaxNode expNode = expression();
		if(expNode != null) {
			newNode.childList.add(expNode);
		}
		match(TokenType.RPAREN);
		SyntaxNode stateChild = statement();
		if(stateChild != null) {
			newNode.childList.add(stateChild);
		}
		match(TokenType.KEY_ELSE);
		SyntaxNode _stateChild = statement();
		if(_stateChild != null) {
			newNode.childList.add(_stateChild);
		}
		return newNode;
	}
	
	private SyntaxNode while_Statement() throws IOException {
		SyntaxNode newNode = new SyntaxNode();
		newNode.nodeType = NodeType.Statement;
		newNode.statement = Statement.While_Statement;
		
		match(TokenType.KEY_WHILE);
		match(TokenType.LPAREN);
		SyntaxNode expNode = expression();
		if(expNode != null) {
			newNode.childList.add(expNode);
		}
		match(TokenType.RPAREN);
		SyntaxNode stateChild = statement();
		if(stateChild != null) {
			newNode.childList.add(stateChild);
		}
		return newNode;
	}
	
	private SyntaxNode print_Statement() throws IOException {
		SyntaxNode newNode = new SyntaxNode();
		newNode.nodeType = NodeType.Statement;
		newNode.statement = Statement.Print_Statement;
		
		match(TokenType.KEY_PRINTLIN);
		match(TokenType.LPAREN);
		SyntaxNode expNode = expression();
		if(expNode != null) {
			newNode.childList.add(expNode);
		}
		match(TokenType.RPAREN);
		match(TokenType.SEMICOLON);
		return newNode;
	}
	
	private SyntaxNode assigan_Statement() throws IOException {
		SyntaxNode assignNode = null;
		pushBackToken();
		switch (token.getType()) {
		case KEY_INT:
			assignNode = varAssigan_Statement();
			return assignNode;
		case RBRACKET:

			assignNode = arrayAssign_Statement();
			return assignNode;
		default:
			syntaxError("Unexpected token at line: "+lexier.line);
			getNextToken();
    		return null;
		}
	}
	
	private SyntaxNode varAssigan_Statement() throws IOException {
		SyntaxNode newNode = new SyntaxNode();
		newNode.nodeType = NodeType.Statement;
		newNode.statement = Statement.VarAssign_Statement;
		
		match(TokenType.IDENTIFIER);
		match(TokenType.ASSIGN);
		SyntaxNode expNode = expression();
		if(expNode != null) {
			newNode.childList.add(expNode);
		}
		match(TokenType.SEMICOLON);
		return newNode;
	}
	
	private SyntaxNode arrayAssign_Statement() throws IOException {
		SyntaxNode newNode = new SyntaxNode();
		newNode.nodeType = NodeType.Statement;
		newNode.statement = Statement.ArrayAssign_Statement;
		
		pushBackToken();
		pushBackToken();
		match(TokenType.IDENTIFIER);
		match(TokenType.LBRACKET);
		SyntaxNode expNode = expression();
		if(expNode != null) {
			newNode.childList.add(expNode);
		}
		match(TokenType.RBRACKET);
		match(TokenType.ASSIGN);
		SyntaxNode _expNode = expression();
		newNode.childList.add(_expNode);
		match(TokenType.SEMICOLON);
		return newNode;
	}
	
	private SyntaxNode expression() throws IOException {
		SyntaxNode newNode = new SyntaxNode();
		switch(token.getType()) {
		case INTEGERLITERAL:
			newNode = int_Expression();
			return newNode;
		case KEY_TRUE:
			newNode = true_Expression();
			return newNode;
		case KEY_FALSE:
			newNode = false_Expression();
			return newNode;
		case IDENTIFIER:
			newNode = identifier_Expression();
			return newNode;
		case KEY_THIS:
			newNode = this_Expression();
			return newNode;
		case KEY_NEW:
			getNextToken();
			if(token.getToken().equals("int")) {
				newNode = newArray_Expression();//此时的token为int，直接从int开始匹配即可
				return newNode;
			}else if(token.getType() == TokenType.IDENTIFIER) {
				newNode = new_Expression();//此时的token为IDENTIFIER
				return newNode;
			}else {
				syntaxError("Unexpected token at line: "+lexier.line);
	    		getNextToken();
	    		return null;
			}
		case LOGICAL_NOT:
			newNode = not_Expression();
			return newNode;
		case LPAREN:
			newNode = brace_Expression();
			return newNode;
		default:
			syntaxError("Unexpected token at line: "+lexier.line);
    		getNextToken();
    		return null;
		}
	}
	
	private SyntaxNode int_Expression() throws IOException {
		SyntaxNode newNode = new SyntaxNode();
		newNode.nodeType = NodeType.Expression;
		newNode.expression = Expression.Int_Expression;
		
		match(TokenType.INTEGERLITERAL);
		SyntaxNode a_statementChild = a_Statement();
		if(a_statementChild != null) {
			newNode.childList.add(a_statementChild);
		}
		
		return newNode;
	}
	private SyntaxNode true_Expression() throws IOException {
		SyntaxNode newNode = new SyntaxNode();
		newNode.nodeType = NodeType.Expression;
		newNode.expression = Expression.True_Expression;
		match(TokenType.KEY_TRUE);
		SyntaxNode a_statementChild = a_Statement();
		if(a_statementChild != null) {
			newNode.childList.add(a_statementChild);
		}
		return newNode;
	}
	private SyntaxNode false_Expression() throws IOException {
		SyntaxNode newNode = new SyntaxNode();
		newNode.nodeType = NodeType.Expression;
		newNode.expression = Expression.False_Expression;
		match(TokenType.KEY_FALSE);
		SyntaxNode a_statementChild = a_Statement();
		if(a_statementChild != null) {
			newNode.childList.add(a_statementChild);
		}
		return newNode;
	}
	private SyntaxNode identifier_Expression() throws IOException {
		SyntaxNode newNode = new SyntaxNode();
		newNode.nodeType = NodeType.Expression;
		newNode.expression = Expression.Identifier_Expression;
		match(TokenType.IDENTIFIER);
		SyntaxNode a_statementChild = a_Statement();
		if(a_statementChild != null) {
			newNode.childList.add(a_statementChild);
		}
		return newNode;
	}
	private SyntaxNode this_Expression() throws IOException {
		SyntaxNode newNode = new SyntaxNode();
		newNode.nodeType = NodeType.Expression;
		newNode.expression = Expression.This_Expression;
		match(TokenType.KEY_THIS);
		SyntaxNode a_statementChild = a_Statement();
		if(a_statementChild != null) {
			newNode.childList.add(a_statementChild);
		}
		return newNode;
	}
	private SyntaxNode newArray_Expression() throws IOException {
		SyntaxNode newNode = new SyntaxNode();
		newNode.nodeType = NodeType.Expression;
		newNode.expression = Expression.NewArray_Expression;
		match(TokenType.KEY_INT);
		match(TokenType.LBRACKET);
		SyntaxNode expChild = expression();
		if(expChild != null) {
			newNode.childList.add(expChild);
		}
		match(TokenType.RBRACKET);
		SyntaxNode a_statementChild = a_Statement();
		if(a_statementChild != null) {
			newNode.childList.add(a_statementChild);
		}
		return newNode;
	}
	private SyntaxNode new_Expression() throws IOException {
		SyntaxNode newNode = new SyntaxNode();
		newNode.nodeType = NodeType.Expression;
		newNode.expression = Expression.New_Expression;
		match(TokenType.IDENTIFIER);
		match(TokenType.LPAREN);
		match(TokenType.RPAREN);
		SyntaxNode a_statementChild = a_Statement();
		if(a_statementChild != null) {
			newNode.childList.add(a_statementChild);
		}
		return newNode;
	}
	private SyntaxNode not_Expression() throws IOException {
		SyntaxNode newNode = new SyntaxNode();
		newNode.nodeType = NodeType.Expression;
		newNode.expression = Expression.Not_Expression;
		
		match(TokenType.LOGICAL_NOT);
		SyntaxNode expChild = expression();
		if(expChild != null) {
			newNode.childList.add(expChild);
		}
		SyntaxNode a_statementChild = a_Statement();
		if(a_statementChild != null) {
			newNode.childList.add(a_statementChild);
		}
		return newNode;
	}
	private SyntaxNode brace_Expression() throws IOException {
		SyntaxNode newNode = new SyntaxNode();
		newNode.nodeType = NodeType.Expression;
		newNode.expression = Expression.Brace_Expression;
		
		match(TokenType.LPAREN);
		SyntaxNode expChild = expression();
		if(expChild != null) {
			newNode.childList.add(expChild);
		}
		match(TokenType.RPAREN);
		SyntaxNode a_statementChild = a_Statement();
		if(a_statementChild != null) {
			newNode.childList.add(a_statementChild);
		}
		return newNode;
	}
	
	private SyntaxNode a_Statement() throws IOException {
		SyntaxNode newNode = null;
		switch(token.getType()) {
		case LOGICAL_AND:
			newNode = op_A();
			return newNode;
		case LESS:
			newNode = op_A();
			return newNode;
		case PLUS:
			newNode = op_A();
			return newNode;
		case MINUS:
			newNode = op_A();
			return newNode;
		case TIMES:
			newNode = op_A();
			return newNode;
		case LBRACKET:
			newNode = exp_A();
			return newNode;
		case POINT:
			getNextToken();
			if(token.getType() == TokenType.KEY_LENGTH) {
				newNode = length_A();
				return newNode;
			}else if(token.getType() == TokenType.IDENTIFIER) {
				newNode = method_A();
				return newNode;
			}else {
				syntaxError("Unexpected token at line: "+lexier.line);
	    		getNextToken();
	    		return null;
			}
		default:
			newNode = null_A();
			return newNode;
		}
	}
	private SyntaxNode op_A() throws IOException {
		SyntaxNode newNode = new SyntaxNode();
		newNode.nodeType = NodeType.A_exp;
		newNode.a_exp = A_exp.op_A;
		TokenType currentType= token.getType();
		if(currentType == TokenType.LOGICAL_AND || currentType == TokenType.LESS || currentType == TokenType.PLUS || currentType ==TokenType. MINUS || currentType == TokenType.TIMES) {
			SyntaxNode expChild = expression();
			if(expChild != null) {
				newNode.childList.add(expChild);
			}
			SyntaxNode a_statementChild = a_Statement();
			if(a_statementChild != null) {
				newNode.childList.add(a_statementChild);
			}
		}else {
			syntaxError("Unexpected token at line: "+lexier.line);
    		getNextToken();
    		return null;
		}
		
		return newNode;
	}
	private SyntaxNode exp_A() throws IOException {
		SyntaxNode newNode = new SyntaxNode();
		newNode.nodeType = NodeType.A_exp;
		newNode.a_exp = A_exp.exp_A;
		match(TokenType.LBRACKET);
		SyntaxNode expChild = expression();
		if(expChild != null) {
			newNode.childList.add(expChild);
		}
		match(TokenType.RBRACKET);
		SyntaxNode a_statementChild = a_Statement();
		if(a_statementChild != null) {
			newNode.childList.add(a_statementChild);
		}
		return newNode;
	}
	private SyntaxNode length_A() throws IOException {
		SyntaxNode newNode = new SyntaxNode();
		newNode.nodeType = NodeType.A_exp;
		newNode.a_exp = A_exp.length_A;
		match(TokenType.KEY_LENGTH);
		SyntaxNode a_statementChild = a_Statement();
		if(a_statementChild != null) {
			newNode.childList.add(a_statementChild);
		}
		return newNode;
	}
	private SyntaxNode method_A() throws IOException {
		SyntaxNode newNode = new SyntaxNode();
		newNode.nodeType = NodeType.A_exp;
		newNode.a_exp = A_exp.method_A;
		
		match(TokenType.IDENTIFIER);
		match(TokenType.LPAREN);
		if(Exp_FirstSet.contains(token.getType())) {
			SyntaxNode expChild = expression();
			if(expChild != null) {
				newNode.childList.add(expChild);
			}
			while(match(TokenType.COMMA)) {
				SyntaxNode _expChild = expression();
				if(_expChild != null) {
					newNode.childList.add(_expChild);
				}
			}
		}
		match(TokenType.RPAREN);
		SyntaxNode a_statementChild = a_Statement();
		if(a_statementChild != null) {
			newNode.childList.add(a_statementChild);
		}
		return newNode;
	}
	private SyntaxNode null_A() {
		SyntaxNode newNode = new SyntaxNode();
		newNode.nodeType = NodeType.A_exp;
		newNode.a_exp = A_exp.null_A;
		
		pushBackToken();
		return newNode;
	}
}
