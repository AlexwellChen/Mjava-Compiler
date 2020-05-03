package parser;

import java.util.*;
import lexier.MjavaLexier;
import lexier.Token;

enum Declaration{
	Goal,
	MainClass,
	ClassDeclaration,
	VarDeclaration,
	MethodDeclaration
}

enum var_Type{
	Int_Array,
	Boolean,
	Int,
	Identifier
}

enum Statement{
	If_Statement,
	While_Statement,
	Print_Statement,
	VarAssign_Statement,
	ArrayAssign_Statement,
	Series_Statement
}

enum Expression{
	Int_Expression,
	True_Expression,
	False_Expression,
	Identifier_Expression,
	This_Expression,
	NewArray_Expression,
	New_Expression,
	Not_Expression,
	Brace_Expression
}

enum A_exp{
	op_A,
	exp_A,
	length_A,
	method_A,
	null_A
}

enum NodeType{
	Declaration,
	Statement,
	Expression,
	A_exp
}

public class SyntaxNode {
	
	public NodeType nodeType;
	public ArrayList<SyntaxNode> childList = new ArrayList<SyntaxNode>();
	public Declaration declaration;
	public Statement statement;
	public Expression expression;
	public A_exp a_exp;
	public int line;
	public var_Type expType;
}
