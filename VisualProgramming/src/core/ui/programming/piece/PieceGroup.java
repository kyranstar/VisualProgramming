package core.ui.programming.piece;

import java.awt.Color;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;

import machine.GamePanel;
import core.ui.programming.ProgrammingSpace;
import core.ui.programming.pieces.ConstantBoolean;
import core.ui.programming.pieces.ConstantNumber;
import core.ui.programming.pieces.Timer;
import core.ui.programming.pieces.gates.arithmetic.Add;
import core.ui.programming.pieces.gates.arithmetic.Divide;
import core.ui.programming.pieces.gates.arithmetic.Modulo;
import core.ui.programming.pieces.gates.arithmetic.Multiply;
import core.ui.programming.pieces.gates.arithmetic.Random;
import core.ui.programming.pieces.gates.arithmetic.Subtract;
import core.ui.programming.pieces.gates.comparison.Equals;
import core.ui.programming.pieces.gates.comparison.GreaterThan;
import core.ui.programming.pieces.gates.comparison.GreaterThanOrEqual;
import core.ui.programming.pieces.gates.comparison.LessThan;
import core.ui.programming.pieces.gates.comparison.LessThanOrEqual;
import core.ui.programming.pieces.gates.logical.LogicalAnd;
import core.ui.programming.pieces.gates.logical.LogicalNot;
import core.ui.programming.pieces.gates.logical.LogicalOr;
import core.ui.programming.pieces.gates.memory.DFlipFlop;
import core.ui.programming.pieces.gates.memory.RSNor;
import core.ui.programming.pieces.gates.memory.TFlipFlop;

public enum PieceGroup{

    ARITHMETIC(new Color(70,180,180),
    		Add.class, 
    		Subtract.class,
    		Multiply.class, 
    		Divide.class, 
    		Modulo.class, 
    		Random.class),
    COMPARISON(new Color(200,60,60),
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
	private PieceGroup(final Color c, final Class<? extends Piece>... classes) {
    	this.c = c;
        for (Class<? extends Piece> pieceClass : classes) {
           classSet.add(pieceClass);
        }
    }
    public static Piece getInstanceOf(final Class<? extends Piece> pieceClass, final ProgrammingSpace space){
    	 try {
             Constructor<? extends Piece> construtor = pieceClass.getDeclaredConstructor(int.class, int.class);
             return ((Piece) construtor.newInstance(space.getX() + space.getWidth()/2, space.getY() + space.getHeight()/2));
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
    	return super.toString().substring(0,1).toUpperCase(Locale.US).concat(super.toString().substring(1).toLowerCase(Locale.US));
    }
	public Color getColor() {
		return c;
	}
}