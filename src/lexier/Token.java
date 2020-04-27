package lexier;

/**
 * @author Alexwell
 *
 */
public class Token {
	private TokenType type;
	private String token;
	private int line;
	private int column;

	/**
	 * 
	 * 词法记号的构造器
	 * 
	 * @param type   词法记号的类型
	 * @param token  词法记号的串值
	 * @param line   词法记号所在的行号，用于调试和检查输出
	 * @param column 词法记号所在的列号，用于调试和检查输出
	 */

	public Token(TokenType type, String token, int line, int column) {
		this.type = type;
		this.token = token;
		this.line = line;
		this.column = column;
	}

	public TokenType getType() {
		return type;
	}

	public String getToken() {
		return token;
	}

	public int getLine() {
		return line;
	}

	public int getColumn() {
		return column;
	}
	
	public enum TokenType {
		/** 变量 **/
		IDENTIFIER, // 标识符

		/** 常量 **/
		INTEGERLITERAL, // 整型常量

		/** 关键字 **/
		KEY_CLASS, // class
		KEY_PUBLIC, // public
		KEY_STATIC, // static
		KEY_VOID, // void
		KEY_MAIN, // main
		KEY_STRING, // string
		KEY_EXTENDS, // extends
		KEY_RETURN, // return
		KEY_INT, // int
		KEY_BOOLEAN, // boolean
		KEY_IF, // if
		KEY_ELSE, // else
		KEY_WHILE, // while
		KEY_PRINTLIN, // System.out.println
		KEY_LENGTH, // length
		KEY_TRUE, // true
		KEY_FALSE, // false
		KEY_THIS, // this
		KEY_NEW, // new

		/** 算数运算符 **/
		PLUS, // +
		MINUS, // -
		TIMES, // *

		/** 赋值 **/
		ASSIGN, // =

		/** 括号 **/
		LPAREN, // (
		RPAREN, // )
		LBRACKET, // [
		RBRACKET, // ]
		LBRACES, // {
		RBRACES, // }

		/** 界符 **/
		COMMA, // 逗号，
		SEMICOLON, // 分号;
		POINT, // 点

		/** 逻辑运算符 **/
		LOGICAL_NOT, // ！
		LOGICAL_AND, // &&

		/** 关系运算符 **/
		LESS ,// <
		
		/**结束**/
		EOF,
		
		/**非法**/
		ILLEGAL_TOKEN
	}
}

