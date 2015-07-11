package hw_encapsulator.reflectionMgr;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;

import hw_encapsulater.model.MethodType;

/**
 * The ReflectionManager class uses the Reflection-API to dynamically invoke methods on an specific class.
 * The Manager filters all methods on the class, depending on the methodType Enum-value.
 * @author michi, manu
 * @version 1.0
 * @param <T> The Generic Type of the Object, the methods are called on.
 */
public class ReflectionManager<T> {

	private T targetObject;
	private ArrayList<Method> methodList = new ArrayList<Method>();
	private ArrayList<String> methodNameList = new ArrayList<String>();
	private MethodType methodType;
	private Method[] classMethods;
	
	/**
	 * Constructor of the ReflectionManager.
	 * @param targetClass the Object of Type <T>, on which the methods are invoked.
	 * @param methodType The Enum-value that indicates, which methods are searched for.
	 */
	public ReflectionManager(T targetObject, MethodType methodType){
		this.targetObject = targetObject;
		this.methodType = methodType;
		classMethods = targetObject.getClass().getMethods();
		initReflecionManagerObjects();
	}
	
	/**
	 * Filters the methods that are searched for.
	 */
	private void initReflecionManagerObjects(){
		if(methodType.equals(MethodType.SET)){
			cleanNoSetMethods();
		}
		else if(methodType.equals(MethodType.GET)){
			cleanNoGetMethods();
		}
		else {
			completeMethodList();
		}
	}
	
	/**
	 * Adds all setter-methods to methodNameList and methodList.
	 */
	private void cleanNoSetMethods(){
		for (int i = 0; i < classMethods.length; i++) {
			if(classMethods[i].getName().startsWith("set")){
				methodList.add(classMethods[i]);
				methodNameList.add(classMethods[i].getName().toUpperCase());
			}
		}
	}
	
	/**
	 * Adds all getter-methods to methodNameList and methodList.
	 */
	private void cleanNoGetMethods(){
		for (int i = 0; i < classMethods.length; i++) {
			if(classMethods[i].getName().startsWith("get")){
				methodList.add(classMethods[i]);
				methodNameList.add(classMethods[i].getName().toUpperCase());
			}
		}
	}
	/**
	 * Adds all methods to methodNameList and methodList.
	 */
	private void completeMethodList(){
		for (int i = 0; i < classMethods.length; i++) {
			methodList.add(classMethods[i]);
			methodNameList.add(classMethods[i].getName().toUpperCase());
		}
	}
	
	/**
	 * Parses the values in String-format to the format expected by the method.
	 * @param paramTypes The expected formats of the parameters the method needs.
	 * @param values the parameters in String format that have to be parsed to the target-format.
	 * @return returns an object-Array containing the parameters in the target-format.
	 */
	private Object[] getParamTypes(Class[] paramTypes, String[] values){
		Object[] ret = new Object[paramTypes.length];
		
		for (int i = 0; i < paramTypes.length; i++) {
			if(paramTypes[i] == int.class){
				ret[i] = Integer.parseInt(values[i]);
			}
			else if(paramTypes[i] == String.class){
				ret[i] = values[i];
			}
			else if(paramTypes[i] == boolean.class){
				ret[i] = Boolean.parseBoolean(values[i]);
			}
		}
		return ret;
	}
	
	/**
	 * Invokes the method, defined by the methodNameToCall-String. The method that will be invoked 
	 * gets more than one parameter, the methodParameter object-Array, which will be casted to the target format.
	 * @param methodNameToCall The name of the method that will be invoked.
	 * @param methodParameter The parameter which will be given to the invoked method.
	 * @throws IllegalArgumentException The parameter cant be casted to the target format.
	 * @throws IllegalAccessException When you try to invoke a method that cant be invoked, for reasons
	 * like calling a protected method from an outter package.
	 * @throws InvocationTargetException If the underlying method throws an exception.
	 * @throws NoSuchMethodException If the method that should be invoked not exists on the class.
	 */
	public void invokeMethodsFromMethodList(String methodNameToCall, Object methodParameter) throws IllegalArgumentException, IllegalAccessException, InvocationTargetException{
		String[] stringArrayValue=null;
		
		if(methodParameter instanceof String[]){
			stringArrayValue = (String[]) methodParameter;
		}
		else if(methodParameter instanceof String){
			stringArrayValue = new String[]{(String)methodParameter};
		}
		else{
			//Platzhalter, muss noch ersetzt werden.
//			throw new Exception();
		}
		
		ArrayList<Integer> index = new ArrayList<Integer>();

		for(int i=0; i<methodNameList.size(); i++)
		{			
			if(methodNameList.get(i).equals(methodNameToCall.toUpperCase()))
			{
				index.add(i);
			}
		}
		if(index.isEmpty()){
//			throw new NoSuchMethodException();
		}
		
		for (Integer i : index) {
			Class[] paramTypes = methodList.get(i).getParameterTypes();
			if(paramTypes.length == stringArrayValue.length){
				methodList.get(i).invoke(targetObject, getParamTypes(paramTypes, stringArrayValue));
			}
		}
	}
	
}
