package hjow.convert.module;

import hjow.methodconverter.Controller;

/**
 * <p>This class has functions which is return help messages.</p>
 * 
 * <p>이 클래스에는 도움말 메시지를 반환하는 함수들이 있습니다.</p>
 * 
 * @author HJOW
 *
 */
public class ScriptHelp
{
	/**
	 * <p>Create object.</p>
	 * 
	 * <p>객체를 생성합니다.</p>
	 */
	public ScriptHelp()
	{
		
	}
	
	/**
	 * <p>Return help message.</p>
	 * 
	 * <p>도움말 메시지를 반환합니다.</p>
	 * 
	 * @return help messages
	 */
	public static String getHelp()
	{
		String locale = System.getProperty("user.language");
		if(locale.equalsIgnoreCase("kr") || locale.equalsIgnoreCase("ko") || locale.equalsIgnoreCase("kor")
				|| locale.equalsIgnoreCase("ko-KR") || locale.equalsIgnoreCase("korean"))
		{
			StringBuffer r = new StringBuffer("");
		      r = r.append("﻿자바스크립트 문법을 사용할 수 있습니다.\n");
		      r = r.append(" \n");
		      r = r.append("[주석]\n");
		      r = r.append("/* 와 */ 사이에 있는 내용은 명령으로 인식하지 않아 무시됩니다.\n");
		      r = r.append("이를 주석이라 합니다.\n");
		      r = r.append(" \n");
		      r = r.append("[세미콜론]\n");
		      r = r.append("각 줄은 세미콜론(;) 기호로 구분해 주는 것이 좋습니다.\n");
		      r = r.append("한 줄에 여러 명령을 적고 싶을 때에도 ; 기호를 사용할 수 있습니다.\n");
		      r = r.append(" \n");
		      r = r.append("[변수]\n");
		      r = r.append("변수는 어떤 값이나 데이터를 저장할 수 있는 저장 공간입니다.\n");
		      r = r.append("변수 하나하나에는 이름이 부여됩니다.\n");
		      r = r.append("변수를 만드려면 대입 연산자를 사용합니다.\n");
		      r = r.append(" \n");
		      r = r.append("[대입 연산자]\n");
		      r = r.append("= 기호는 새로운 변수를 만들거나, 변수에 값을 넣을 때 사용됩니다.\n");
		      r = r.append(" \n");
		      r = r.append("예를 들어, 새로운 변수 a 를 만들어 1 이라는 숫자 값을 넣고 싶다면\n");
		      r = r.append("a = 1;\n");
		      r = r.append("이라는 명령을 사용합니다.\n");
		      r = r.append("이미 a 라는 변수가 있었다면, 이전에 변수 a 에 들어 있던 값이 날아가고 새로 넣은 값 1이 들어갑니다.\n");
		      r = r.append(" \n");
		      r = r.append("[데이터 종류 및 연산 기호]\n");
		      r = r.append("데이터 종류는 정수, 실수, 논리, 텍스트(문자열), 객체가 있습니다.\n");
		      r = r.append(" \n");
		      r = r.append("정수는 소수 자리가 없는 값을 말합니다. 1, 2 등의 값이 있습니다.\n");
		      r = r.append("실수는 소수 자리가 있는 값을 말합니다. 1.2, 5.4 등의 값이 있습니다.\n");
		      r = r.append("논리는 true, false 둘 중 하나의 값을 말합니다.\n");
		      r = r.append(" \n");
		      r = r.append("정수와 실수 데이터는 기본 연산 +, -, *, / 이 가능합니다.\n");
		      r = r.append("정수 데이터끼리는 % 연산 또한 가능합니다.\n");
		      r = r.append(" \n");
		      r = r.append("예를 들어, 변수 a 에, 5 + 10 의 결과 값을 넣고 싶으면\n");
		      r = r.append("a = 5 + 10;\n");
		      r = r.append("이라는 명령을 사용합니다.\n");
		      r = r.append(" \n");
		      r = r.append("이전에 이미 b, c 라는 변수에 각각 어떤 정수 값이 들어있고, 변수 a에 이 둘을 합한 값을 넣고 싶으면\n");
		      r = r.append("a = b + c;\n");
		      r = r.append("라는 명령을 사용합니다.\n");
		      r = r.append(" \n");
		      r = r.append("논리 값에는 ! 연산이 가능합니다.\n");
		      r = r.append("! 는, true 를 false,로, false 를 true로 바꾸는 연산입니다.\n");
		      r = r.append(" \n");
		      r = r.append("예를 들어, a 라는 변수에 true 가 들어 있을 때\n");
		      r = r.append("a = !a;\n");
		      r = r.append("라는 명령을 실행하면, a 에는 false 가 들어가게 됩니다.\n");
		      r = r.append(" \n");
		      r = r.append("텍스트(문자열) 값을 새로 만드려면 따옴표(')를 사용해야 합니다.\n");
		      r = r.append("예를 들어 a 라는 변수에 \"Hello\" 라는 텍스트를 넣고 싶으면\n");
		      r = r.append("a = 'Hello';\n");
		      r = r.append("라고 합니다.\n");
		      r = r.append(" \n");
		      r = r.append("텍스트 값의 덧셈은 이어 붙이기가 됩니다.\n");
		      r = r.append("예를 들어, 'Hello' + 'World' 는 'HelloWorld'와 같습니다.\n");
		      r = r.append("띄어쓰기 또한 텍스트 값이므로 ' ' 또한 텍스트입니다.\n");
		      r = r.append(" \n");
		      r = r.append("텍스트 값에 대한 -, *, / 연산은 불가능합니다.\n");
		      r = r.append(" \n");
		      r = r.append("[비교 연산 기호]\n");
		      r = r.append("비교 연산 기호는 ==, !=, <, >, <=, >= 등이 있습니다.\n");
		      r = r.append(" \n");
		      r = r.append("== 는 양쪽 값이 같은지를 비교합니다. 양쪽 값이 같은 경우 true, 다른 경우 false를 반환합니다.\n");
		      r = r.append("!= 는 양쪽 값이 다른지를 비교합니다. 다른 경우 true, 같은 경우 false 를 반환합니다.\n");
		      r = r.append(" \n");
		      r = r.append("<, >, <=, >= 기호는 양쪽 값이 정수 혹은 실수 데이터인 경우에만 사용이 가능합니다.\n");
		      r = r.append(" \n");
		      r = r.append("예를 들어, 변수 a 에 들어 있는 값이 변수 b 에 들어 있는 값보다 크거나 같은지를 변수 c에 넣으려면\n");
		      r = r.append("c = a >= b;\n");
		      r = r.append("라는 명령을 사용합니다. 이 경우 실제로 변수 a의 값이 변수 b의 값보다 크다면 변수 c에 true 가 들어갑니다.\n");
		      r = r.append(" \n");
		      r = r.append("[연산 순서 바꾸기]\n");
		      r = r.append("연산 순서를 바꾸기 위해 괄호를 사용할 수 있습니다. 이 경우 반드시 일반 괄호를 사용해야 합니다.\n");
		      r = r.append("= 기호는 명령 내에 있는 연산 기호 중 반드시 왼쪽에 위치해야 합니다.\n");
		      r = r.append(" \n");
		      r = r.append("[조건문]\n");
		      r = r.append("특정 조건이 만족해야 명령을 실행하도록 하려면 if 명령을 사용합니다.\n");
		      r = r.append("if 뒤에 괄호를 열고, 조건문을 넣은 후 조건문을 닫습니다.\n");
		      r = r.append("이후 중괄호를 열고 조건 만족 시 실행할 명령문을 넣고 중괄호를 닫습니다.\n");
		      r = r.append(" \n");
		      r = r.append("여기서 조건문은 실행 결과가 논리 값인 명령문입니다. 조건문 대신 논리 값이 들어 있는 변수 이름도 사용이 가능합니다.\n");
		      r = r.append(" \n");
		      r = r.append("예를 들어, 변수 a 에 있는 값이 5보다 크면 변수 c 에 1을 넣고 싶다면\n");
		      r = r.append("if(a > 5)\n");
		      r = r.append("{\n");
		      r = r.append("   c = 1;\n");
		      r = r.append("}\n");
		      r = r.append("라는 명령을 사용합니다.\n");
		      r = r.append(" \n");
		      r = r.append("명령문이 한 줄인 경우 중괄호를 쓰지 않고도 사용이 가능합니다.\n");
		      r = r.append("위의 예를 한 줄 표현으로 보면\n");
		      r = r.append(" \n");
		      r = r.append("if(a > 5) c = 1;\n");
		      r = r.append(" \n");
		      r = r.append("이렇게 사용이 가능합니다.\n");
		      r = r.append(" \n");
		      r = r.append("else 구문은 if 문과 같이 사용해야 합니다.\n");
		      r = r.append("조건에 만족하지 않은 경우를 따로 나누어 다른 명령을 실행하려는 경우 사용됩니다.\n");
		      r = r.append(" \n");
		      r = r.append("if(a > 5)\n");
		      r = r.append("{\n");
		      r = r.append("   c = 1;\n");
		      r = r.append("}\n");
		      r = r.append("else\n");
		      r = r.append("{\n");
		      r = r.append("   c = 2;\n");
		      r = r.append("}\n");
		      r = r.append(" \n");
		      r = r.append("이러한 방식으로 사용됩니다.\n");
		      r = r.append("else문 또한 한 줄 표현 방식을 사용할 수 있으므로\n");
		      r = r.append(" \n");
		      r = r.append("if(a > 5)\n");
		      r = r.append("{\n");
		      r = r.append("   c = 1;\n");
		      r = r.append("}\n");
		      r = r.append("else if(a > 3)\n");
		      r = r.append("{\n");
		      r = r.append("   c = 2;\n");
		      r = r.append("}\n");
		      r = r.append("else\n");
		      r = r.append("{\n");
		      r = r.append("   c = 3;\n");
		      r = r.append("}\n");
		      r = r.append(" \n");
		      r = r.append("이러한 방식으로 사용할 수도 있습니다.\n");
		      r = r.append(" \n");
		      r = r.append("[반복문]\n");
		      r = r.append("특정 조건이 만족하면 어떠한 명령을 계속 반복하게 할 수 있습니다.\n");
		      r = r.append("반복 중 해당 조건이 만족하지 않게 되면 반복이 종료됩니다.\n");
		      r = r.append("이러한 반복을 위해 while 명령을 사용합니다.\n");
		      r = r.append(" \n");
		      r = r.append("while 명령도 if 명령과 비슷한 명령문 구조를 가집니다.\n");
		      r = r.append(" \n");
		      r = r.append("예를 들어, 변수 a에 있는 값이 1000 보다 커질 때까지 변수 a의 값을 두 배로 늘리고 싶다면\n");
		      r = r.append("while(a <= 1000)\n");
		      r = r.append("{\n");
		      r = r.append("   a = a * 2;\n");
		      r = r.append("}\n");
		      r = r.append("라는 명령을 사용합니다. 조건문 자리에는 변수 a의 값이 1000보다 작거나 같다는 조건을 넣었습니다.\n");
		      r = r.append("이렇게 하면 변수 a의 값이 1000보다 작거나 같지 않을 때까지(즉, 1000보다 커질 때까지) 2배를 하는 명령이 반복 실행됩니다.\n");
		      r = r.append(" \n");
		      r = r.append("조건문은 언젠가는 만족되지 않게 되는 것으로 사용하세요.\n");
		      r = r.append("그렇지 않으면 프로그램이 종료될 때까지 계속해서 반복될 것입니다.\n");
		      r = r.append("이를 무한 반복(혹은 무한 루프) 현상이라고 합니다.\n");
		      r = r.append(" \n");
		      r = r.append("반복 작업을 위해 while 대신 for 명령을 사용할 수도 있습니다.\n");
		      r = r.append("0부터 1000까지의 정수들을 모두 더하는 계산을 수행하는 명령을 예제로 보겠습니다.\n");
		      r = r.append(" \n");
		      r = r.append("a = 0;\n");
		      r = r.append("b = 0;\n");
		      r = r.append("while(b < 1000)\n");
		      r = r.append("{\n");
		      r = r.append("   a = a + b;\n");
		      r = r.append("   b = b + 1;\n");
		      r = r.append("}\n");
		      r = r.append(" \n");
		      r = r.append("위의 명령은 다음의 명령과 같은 작업을 합니다.\n");
		      r = r.append(" \n");
		      r = r.append("a = 0;\n");
		      r = r.append("for(b = 0; b < 1000; b = b + 1)\n");
		      r = r.append("{\n");
		      r = r.append("   a = a + b;\n");
		      r = r.append("}\n");
		      r = r.append(" \n");
		      r = r.append("이렇게 for 문은 순차적인 계산에 보다 보기 좋은 명령문을 사용하고 싶을 때 사용합니다.\n");
		      r = r.append("for 뒤에 괄호를 열고, 반복 전 수행할 작업을 적습니다. 이후 세미콜론 기호를 쓰고, 반복에 쓰일 조건문을 적습니다.\n");
		      r = r.append("이후 다시 세미콜론 기호를 쓰고, 매 반복 마다 추가로 수행해야 할 명령을 쓰고 괄호를 닫습니다.\n");
		      r = r.append("이후 중괄호를 열고 반복할 명령문을 쓰고 중괄호를 닫으면 됩니다.\n");
		      r = r.append(" \n");
		      r = r.append("[함수]\n");
		      r = r.append("자주 사용하는 명령문들을 묶어 이름을 붙여 쓸 수 있습니다. 이 묶은 것을 함수라고 합니다.\n");
		      r = r.append(" \n");
		      r = r.append("변수 a 를 2배로 하는 함수를 b 라는 이름으로 만들어 보면\n");
		      r = r.append("function b()\n");
		      r = r.append("{\n");
		      r = r.append("  a = a * 2;\n");
		      r = r.append("}\n");
		      r = r.append("라는 명령을 사용합니다.\n");
		      r = r.append("이렇게 만든 함수를 실행하려면\n");
		      r = r.append("b();\n");
		      r = r.append("라는 명령을 사용합니다.\n");
		      r = r.append(" \n");
		      r = r.append("function 뒤에 한 칸을 띄고, 만들 함수 이름을 적습니다. 괄호를 열고 바로 닫습니다.\n");
		      r = r.append("이후 중괄호를 열고, 함수 실행 시 실행할 명령들을 적은 후 중괄호를 닫습니다.\n");
		      r = r.append(" \n");
		      r = r.append("괄호를 닫지 않고, 이전에 쓰지 않은 변수 이름을 쓰고 닫은 경우, 매개변수가 있는 것으로 간주됩니다.\n");
		      r = r.append("매개변수는 함수 실행에 필요한 데이터로 사용됩니다.\n");
		      r = r.append("return 문은 함수 실행 결과를 명시합니다.\n");
		      r = r.append(" \n");
		      r = r.append("예를 들어, 어떤 두 변수 중 더 큰 값이 들어 있는 변수의 값을 구하는 함수를 만들고 싶다면\n");
		      r = r.append("function c(a, b)\n");
		      r = r.append("{\n");
		      r = r.append("   if(a > b) return a;\n");
		      r = r.append("   else return b;\n");
		      r = r.append("}\n");
		      r = r.append("이렇게 만듭니다.\n");
		      r = r.append(" \n");
		      r = r.append("위의 예제와 같이 c 라는 함수를 만들었다면\n");
		      r = r.append(" \n");
		      r = r.append("a = 5;\n");
		      r = r.append("b = 4;\n");
		      r = r.append("d = c(a, b);\n");
		      r = r.append(" \n");
		      r = r.append("이러한 방식으로 사용합니다.\n");
		      r = r.append("위의 예제에서 d 라는 변수에는, 함수 c를 만드는 예제에서 return 뒤에 위치한 변수의 값이 들어가게 됩니다.\n");
		      r = r.append("이를 반환 이라고 합니다.\n");
		      r = r.append(" \n");
		      r = r.append("[객체]\n");
		      r = r.append("자바스크립트에서는 객체를 만들 수는 없지만, 이미 정의되어 있는 객체를 사용할 수 있습니다.\n");
		      r = r.append("메소드 변환기 스크립트에서는 javaobject와 controlobject 이 두 가지 객체를 사용할 수 있습니다.\n");
		      r = r.append(" \n");
		      r = r.append("객체에는 여러 변수와 함수들이 포함되어 있습니다.\n");
		      r = r.append(" \n");
		      r = r.append("객체 사용에는 마침표(.) 기호가 사용됩니다.\n");
		      r = r.append(" \n");
		      r = r.append("예를 들어, javaobject 객체에 있는 println 이라는 함수를 사용하려면\n");
		      r = r.append("javaobject.println();\n");
		      r = r.append("이라는 명령을 사용합니다.\n");
		      r = r.append(" \n");
		      r = r.append("이미 객체에 포함되어 있는 함수만을 사용할 수 있습니다.\n");
		      r = r.append(" \n");
		      r = r.append("javaobject 와 controlobject 에 포함된 함수들은 홈페이지에서 확인해 보세요.\n");
		      r = r.append("javaobject 에 대한 도움말은 http://netstorm.woobi.co.kr/methodconverter/doc/hjow/convert/javamethods/MethodContainer.html\n");
		      r = r.append("controlobject 에 대한 도움말은 http://netstorm.woobi.co.kr/methodconverter/doc/hjow/convert/javamethods/ControlContainer.html\n");
		      r = r.append("에서 확인할 수 있습니다.\n");
		      return r.toString();
		}
		else
		{
			StringBuffer r = new StringBuffer("");
		      r = r.append("﻿You can use JavaScript code.\n");
		      r = r.append("Two special object javaobject, controlobject are available.\n");
		      r = r.append("You can see helps of those objects at following website.\n");
		      r = r.append("javaobject : http://netstorm.woobi.co.kr/methodconverter/doc/hjow/convert/javamethods/MethodContainer.html\n");
		      r = r.append("controlobject : http://netstorm.woobi.co.kr/methodconverter/doc/hjow/convert/javamethods/ControlContainer.html\n");
		      return r.toString();
		}
	}
	
	/**
	 * <p>Print help message.</p>
	 * 
	 * <p>도움말 메시지를 출력합니다.</p>
	 * 
	 */
	public void help()
	{
		Controller.println("\n" + getHelp());
	}
}
