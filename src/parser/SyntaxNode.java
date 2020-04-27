package parser;

import java.util.*;
import lexier.MjavaLexier;
import lexier.Token;

enum Declaration{
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
	ArrayAssign_Statement
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

enum NodeType{
	Declaration,
	Statement,
	Expression
}

public class SyntaxNode {
	
	private NodeType nodeType;
	private ArrayList<SyntaxNode> childList = new ArrayList<SyntaxNode>();
	private Declaration declaration;
	private Statement statement;
	private Expression expression;
	private int line;
	private var_Type expType;
}
