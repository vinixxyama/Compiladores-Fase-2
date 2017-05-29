/*    Vinicius Yamamoto RA:490105
      Daniel Valim            RA:511315
*/
      
package Lexer;

public enum Symbol {
      EOF("eof"),
      IDENT("Identifier"),
      NUMBER("Number"),
      PLUS("+"),
      MINUS("-"),
      MULT("*"),
      EXPONENCIAL("^"),
      DIV("/"),
      LT("<"),
      LE("<="),
      GT(">"),
      GE(">="),
      NEQ("!="),
      EQ("=="),
      LTGT("<>"),
      ASSIGN("="),
      LEFTPAR("("),
      RIGHTPAR(")"),
      SEMICOLON(";"),
      BEGIN("begin"),
      END("end"),
      IF("if"),
      THEN("then"),
      ELSE("else"),
      ENDIF("endif"),
      COMMA(","),
      READ("read"),
      WRITE("write"),
      PROGRAM("program"),
      EM("!"),
      DQ(":="),
      IBAR("\'"),
      COLON(":"),
      ALVEOLAR("!"),
      VOID("void"),
      MAIN("main"),
      INT("int"),
      FLOAT("float"),
      DOUBLE("double"),
      CHAR("char"),
      STRING("string"),
      READINTEGER("readInteger"),
      READDOUBLE("readDouble"),
      READCHAR("readChar"),
      WHILE("while"),
      BREAK("break"),
      PRINT("print"),
      LEFTBRACES("{"),
      RIGHTBRACES("}"),
      LEFTBRACKETS("["),
      RIGHTBRACKETS("]"),
      AMPERSAND("&"),
      DAMPERSAND("&&"),
      PCT("%"),
      ERROR("error"),
      HASHTAG("#"),
      VBAR("|"),
      MAGNITUDE("||"),
      DOT("."),
      UNDERSCORE("_"),
      NOT("not"),
      AND("and"),
      OR("or"),
      FALSE("False"),
      TRUE("True"),
      BOOLEAN("boolean"),
      INTEGER("integer"),
      FOR("for"),
      INRANGE("inrange");
      Symbol(String name) {
          this.name = name;
      }
      public String toString() { 
            return name;
      }
      public String name;
}
  
