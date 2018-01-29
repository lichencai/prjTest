package other;

import java.lang.reflect.Field;

/**
 * 通过反射获取某个类某个字段的值
 */
public class ReadObjectField {
	public static void main(String[] args) throws IllegalArgumentException, IllegalAccessException {
		String[] fieldName = {"field1","field2","name","age"};
		ObjectVO vo = new ObjectVO();
		vo.setField1("field1");
		vo.setField2("field2");
		vo.setAge("age");
		vo.setName("name");
		Field[] fields = vo.getClass().getDeclaredFields();
		for(Field filed : fields){
			filed.setAccessible(true);
			for(String name : fieldName){
				if(filed.getType().getName().equals(java.lang.String.class.getName()) && filed.getName().equals(name)){
					System.out.println(filed.get(vo));
					break;
				}
			}
		}
	}
}
