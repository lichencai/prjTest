package nio.buffer;

import java.nio.ByteBuffer;
/** ��ByteBuffer�����л�ȡ�ʹ���޷���ֵ�Ĺ����ࡣ<br /> 
 *  �������еĺ������Ǿ�̬�ģ����Ҵ���һ��ByteBuffer������<br />  
 *  ����java���ṩ�޷���ԭʼ���ͣ�ÿ���ӻ������ж������޷���ֵ�������������,��һ���������������С� <br />  
 *  getUnsignedByte()����һ��short����, getUnsignedShort( ) * ����һ��int���ͣ���getUnsignedInt()����һ��long�͡�<br /> 
 *  ����û�л����������洢���ص����ݣ����û��getUnsignedLong( )��<br />  
 *  �����Ҫ������BigInteger�ĺ�������ִ�С�<br /> 
 *  ͬ������ź���Ҫȡһ��������������������͵�ֵ��<br /> 
 *  putUnsignedByteȡһ��short�Ͳ������ȵȡ�
 **/
public class Unsigned {
	public static short getUnsignedByte (ByteBuffer bb) { return ((short)(bb.get( ) & 0xff)); }
	public static void putUnsignedByte (ByteBuffer bb, int value) { bb.put ((byte)(value & 0xff)); }
	public static int getUnsignedShort (ByteBuffer bb) { return (bb.getShort( ) & 0xffff); }
	public static void putUnsignedShort (ByteBuffer bb, int value) { bb.putShort ((short)(value & 0xffff)); }
}
