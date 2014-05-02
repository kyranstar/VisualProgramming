package piece;

import java.awt.Color;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.HashSet;
import java.util.Set;

import machine.ProgrammingSpace;
import pieces.ConstantBoolean;
import pieces.ConstantNumber;
import pieces.Timer;
import pieces.gates.arithmetic.Add;
import pieces.gates.arithmetic.Divide;
import pieces.gates.arithmetic.Modulo;
import pieces.gates.arithmetic.Multiply;
import pieces.gates.arithmetic.Random;
import pieces.gates.arithmetic.Subtract;
import pieces.gates.bitwise.BitwiseAnd;
import pieces.gates.bitwise.BitwiseLeftshift;
import pieces.gates.bitwise.BitwiseNand;
import pieces.gates.bitwise.BitwiseNor;
import pieces.gates.bitwise.BitwiseNot;
import pieces.gates.bitwise.BitwiseOr;
import pieces.gates.bitwise.BitwiseRightshift;
import pieces.gates.bitwise.BitwiseXnor;
import pieces.gates.bitwise.BitwiseXor;
import pieces.gates.comparison.Equals;
import pieces.gates.comparison.GreaterThan;
import pieces.gates.comparison.GreaterThanOrEqual;
import pieces.gates.comparison.LessThan;
import pieces.gates.comparison.LessThanOrEqual;
import pieces.gates.logical.LogicalAnd;
import pieces.gates.logical.LogicalNot;
import pieces.gates.logical.LogicalOr;
import pieces.gates.memory.DFlipFlop;
import pieces.gates.memory.RSNor;
import pieces.gates.memory.TFlipFlop;

public enum PieceGroup{

    BITWISE(new Color(200,60,60),
    		BitwiseAnd.class, 
    		BitwiseNand.class, 
    		BitwiseNor.class, 
    		BitwiseNot.class,
            BitwiseOr.class, 
            BitwiseXor.class, 
            BitwiseXnor.class, 
            BitwiseLeftshift.class,
            BitwiseRightshift.class),
    ARITHMETIC(new Color(70,180,180),
    		Add.class, 
    		Subtract.class,
    		Multiply.class, 
    		Divide.class, 
    		Modulo.class, 
    		Random.class),
    COMPARISON(new Color(150,150,130),
    		LessThan.class,
    		GreaterThan.class,
    		Equals.class,
    		GreaterThanOrEqual.class,
    		LessThanOrEqual.class),
    LOGICAL(new Color(130,80,140),
    		LogicalNot.class,
    		LogicalAnd.class,
    		LogicalOr.class),
    MEMORY(new Color(130,60,130),
    		RSNor.class,
    		TFlipFlop.class,
    		DFlipFlop.class),
    MISC(new Color(120,120,120),
    		ConstantNumber.class,
    		ConstantBoolean.class,
    		Timer.class);

    private Set<Class<? extends Piece>> classSet = new HashSet<Class<? extends Piece>>();
    private Color c;
    @SafeVarargs
	private PieceGroup(Color c, Class<? extends Piece>... classes) {
    	this.c = c;
        for (Class<? extends Piece> pieceClass : classes) {
           classSet.add(pieceClass);
        }
    }
    public static Piece getInstanceOf(Class<? extends Piece> pieceClass, ProgrammingSpace space){
    	 try {
             Constructor<? extends Piece> construtor = pieceClass.getDeclaredConstructor(int.class, int.class);
             return ((Piece) construtor.newInstance(space.getX() + ProgrammingSpace.getWidth()/2, space.getY() + ProgrammingSpace.getHeight()/2));
         } catch (NoSuchMethodException ex) {
             processException();
         } catch (SecurityException ex) {
             processException();
         } catch (InstantiationException ex) {
             processException();
         } catch (IllegalAccessException ex) {
             processException();
         } catch (IllegalArgumentException ex) {
             processException();
         } catch (InvocationTargetException ex) {
              processException();
         }
		return null;
    }
    public static PieceGroup[] getGroups(){
    	return values();
    }
    public Set<Class<? extends Piece>> getClasses () {
        return classSet;
    }

    private static void processException () {
        throw new IllegalArgumentException("Something whent wrong with the init of the Enum");
    }
    @Override
    public String toString(){
    	return super.toString().substring(0,1).toUpperCase().concat(super.toString().substring(1).toLowerCase());
    }
	public Color getColor() {
		return c;
	}
}