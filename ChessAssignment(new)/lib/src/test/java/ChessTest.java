import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.lang.reflect.Modifier;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.function.Consumer;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import com.google.common.truth.Truth;

class ChessTest {
	private static final Class<?> CLASS = Chess.class;
	@Nested
	class NonFunctionalTesting {
		@Test
		void testFields() {
			Consumer<Class<?>> fieldsNotStaticUnlessFinal = 
					c -> Arrays.stream  ( c.getDeclaredFields() )
					           .filter  ( f->!f.isSynthetic() )
					           .forEach ( f->{
					        	   int mods = f.getModifiers();
					        	   if (Modifier.isStatic(mods)) {
						        	   Truth.assertWithMessage(String.format("static field '%s.%s' must be final", c.getSimpleName(), f.getName() ))
						        	        .that( Modifier.isFinal( mods ))
						        	        .isTrue();
					        	   }
					           });
			Consumer<Class<?>> fieldsPrivate = 
					c -> Arrays.stream  ( c.getDeclaredFields() )
	                           .filter  ( f->!f.isSynthetic() )
	                           .forEach ( f->Truth.assertWithMessage( String.format("field '%s.%s' is not private", c.getSimpleName(), f.getName() ))
	                        		              .that( Modifier.isPrivate( f.getModifiers() ))
	                        		              .isTrue() );
		    fieldsPrivate             .accept( CLASS );
			fieldsNotStaticUnlessFinal.accept( CLASS );
		}
	}
	@Nested
	class FunctionalTesting {
		@Test
		void test01(@TempDir Path folder) throws FileNotFoundException {
			// create file
			Path path = folder.resolve( "foo.txt" );
			// add initial input file content
			try (var write = new PrintWriter( path.toFile() )) {
				String[] board = {
						"........",
						"........",
						"........",
						"........",
						"........",
						"........",
						"........",
						"........" 
				};
				for (var s : board) {
					write.println( s );
				}
			}
			Character expected = '-';
			Character actual   = Chess.go( path.toFile() );
			Truth.assertWithMessage( "No king is in check: board is empty." ).that( actual ).isEqualTo( expected );
		}
		@Test
		void test02(@TempDir Path folder) throws FileNotFoundException {
			// create file
			Path path = folder.resolve( "foo.txt" );
			// add initial input file content
			try (var write = new PrintWriter( path.toFile() )) {
				String[] board = {
						"rnbqkbnr",
						"pppppppp",
						"........",
						"........",
						"........",
						"........",
						"PPPPPPPP",
						"RNBQKBNR" 
				};
				for (var s : board) {
					write.println( s );
				}
			}
			Character expected = '-';
			Character actual   = Chess.go( path.toFile() );
			Truth.assertWithMessage( "No king is in check." ).that( actual ).isEqualTo( expected );
		}
		@Test
		void test03(@TempDir Path folder) throws FileNotFoundException {
			// create file
			Path path = folder.resolve( "foo.txt" );
			// add initial input file content
			try (var write = new PrintWriter( path.toFile() )) {
				String[] board = {
						"N.Q.....",
						"........",
						"Pk......",
						"........",
						"........",
						"........",
						"......K.",
						"........"
				};
				for (var s : board) {
					write.println( s );
				}
			}
			Character expected = 'N';
			Character actual   = Chess.go( path.toFile() );
			Truth.assertWithMessage( "Black king is in check by knight." ).that( actual ).isEqualTo( expected );
		}
		@Test
		void test04(@TempDir Path folder) throws FileNotFoundException {
			// create file
			Path path = folder.resolve( "bar.txt" );
			// add initial input file content
			try (var write = new PrintWriter( path.toFile() )) {
				String[] board = {
						"......R.",
						".....NBR",
						"....QBk.",
						".....R.P",
						"....K.N.",
						"........",
						"........",
				        "........"
				};
				for (var s : board) {
					write.println( s );
				}
			}
			Character expected = 'P';
			Character actual   = Chess.go( path.toFile() );
			Truth.assertWithMessage( "Black king is in check by pawn." ).that( actual ).isEqualTo( expected );
		}
		@Test
		void test05(@TempDir Path folder) throws FileNotFoundException {
			// create file
			Path path = folder.resolve( "bar.txt" );
			// add initial input file content
			try (var write = new PrintWriter( path.toFile() )) {
				String[] board = {
						"........",
						".B......",
						"........",
						"....B...",
						"....Q.NR",
						"........",
						".......P",
						"....RKpk"
				};
				for (var s : board) {
					write.println( s );
				}
			}
			Character expected = 'Q';
			Character actual   = Chess.go( path.toFile() );
			Truth.assertWithMessage( "Black king is in check by queen." ).that( actual ).isEqualTo( expected );
		}
		@Test
		void test06(@TempDir Path folder) throws FileNotFoundException {
			// create file
			Path path = folder.resolve( "bar.txt" );
			// add initial input file content
			try (var write = new PrintWriter( path.toFile() )) {
				String[] board = {
						"..k.....",
						"ppp.pppp",
						"........",
						".R...B..",
						"........",
						"........",
						"PPPPPPPP",
				        "K......."
				};
				for (var s : board) {
					write.println( s );
				}
			}
			Character expected = 'B';
			Character actual   = Chess.go( path.toFile() );
			Truth.assertWithMessage( "Black king is in check by bishop." ).that( actual ).isEqualTo( expected );
		}
		@Test
		void test07(@TempDir Path folder) throws FileNotFoundException {
			// create file
			Path path = folder.resolve( "foo.txt" );
			// add initial input file content
			try (var write = new PrintWriter( path.toFile() )) {
				String[] board = {
						"........",
						"....r...",
						"..q.p.b.",
						"...n.p..",
						"....K...",
						"........",
						"........",
				        "....k..."
				};
				for (var s : board) {
					write.println( s );
				}
			}
			Character expected = 'p';
			Character actual   = Chess.go( path.toFile() );
			Truth.assertWithMessage( "White king is in check by pawn." ).that( actual ).isEqualTo( expected );
		}
		@Test
		void test08(@TempDir Path folder) throws FileNotFoundException {
			// create file
			Path path = folder.resolve( "foo.txt" );
			// add initial input file content
			try (var write = new PrintWriter( path.toFile() )) {
				String[] board = {
						"rnbqk.nr",
						"ppp..ppp",
						"....p...",
						"...p....",
						".bPP....",
						".....N..",
						"PP..PPPP",
				        "RNBQKB.R"
				};
				for (var s : board) {
					write.println( s );
				}
			}
			Character expected = 'b';
			Character actual   = Chess.go( path.toFile() );
			Truth.assertWithMessage( "White king is in check by bishop." ).that( actual ).isEqualTo( expected );
		}
		@Test
		void test09(@TempDir Path folder) throws FileNotFoundException {
			// create file
			Path path = folder.resolve( "foo.txt" );
			// add initial input file content
			try (var write = new PrintWriter( path.toFile() )) {
				String[] board = {
						"rnb.k.nr",
						"pppp.ppp",
						"........",
						"..b.p...",
						".P..P...",
						"P.N.....",
						"..PP.qPP",
				        "R.BQKBNR"
				};
				for (var s : board) {
					write.println( s );
				}
			}
			Character expected = 'q';
			Character actual   = Chess.go( path.toFile() );
			Truth.assertWithMessage( "White king is in check by queen." ).that( actual ).isEqualTo( expected );
		}
		@Test
		void test10(@TempDir Path folder) throws FileNotFoundException {
			// create file
			Path path = folder.resolve( "bar.txt" );
			// add initial input file content
			try (var write = new PrintWriter( path.toFile() )) {
				String[] board = {
						"n.......",
						".q......",
						"b.......",
						"K......r",
						"b.......",
						".knr....",
						"........",
				        "........"
				};
				for (var s : board) {
					write.println( s );
				}
			}
			Character expected = 'r';
			Character actual   = Chess.go( path.toFile() );
			Truth.assertWithMessage( "White king is in check by rook." ).that( actual ).isEqualTo( expected );
		}
		@Test
		void test11(@TempDir Path folder) throws FileNotFoundException {
			// create file
			Path path = folder.resolve( "jinx.txt" );
			// add initial input file content
			try (var write = new PrintWriter( path.toFile() )) {
				String[] board = {
						"n....k..",
						".q......",
						"b.......",
						".......R",
						"B.......",
						".Knr....",
						"........",
				        "........"
				};
				for (var s : board) {
					write.println( s );
				}
			}
			Character expected = 'q';
			Character actual   = Chess.go( path.toFile() );
			Truth.assertWithMessage( "White king is in check by queen." ).that( actual ).isEqualTo( expected );
		}
		@Test
		void test12(@TempDir Path folder) throws FileNotFoundException {
			// create file
			Path path = folder.resolve( "jinx.txt" );
			// add initial input file content
			try (var write = new PrintWriter( path.toFile() )) {
				String[] board = {
						"..Q.....",
						"........",
						"P.N.....",
						"....k...",
						"........",
						"........",
						"......K.",
  				        "........"
				};
				for (var s : board) {
					write.println( s );
				}
			}
			Character expected = 'N';
			Character actual   = Chess.go( path.toFile() );
			Truth.assertWithMessage( "Black king is in check by knight." ).that( actual ).isEqualTo( expected );
		}
		@Test
		void test13(@TempDir Path folder) throws FileNotFoundException {
			// create file
			Path path = folder.resolve( "foo.txt" );
			// add initial input file content
			try (var write = new PrintWriter( path.toFile() )) {
				String[] board = {
						"..Q.....",
						"........",
						"P.......",
						"....k...",
						"........",
						".N.....b",
						"......K.",
				        "........"
				};
				for (var s : board) {
					write.println( s );
				}
			}
			Character expected = 'b';
			Character actual   = Chess.go( path.toFile() );
			Truth.assertWithMessage( "White king is in check by bishop." ).that( actual ).isEqualTo( expected );
		}
		@Test
		void test14(@TempDir Path folder) throws FileNotFoundException {
			// create file
			Path path = folder.resolve( "bar.txt" );
			// add initial input file content
			try (var write = new PrintWriter( path.toFile() )) {
				String[] board = {
						"..Q.....",
						"........",
						"P.......",
						"...qk...",
						"........",
						".N......",
						"......K.",
				        "........"
				};
				for (var s : board) {
					write.println( s );
				}
			}
			Character expected = 'q';
			Character actual   = Chess.go( path.toFile() );
			Truth.assertWithMessage( "White king is in check by queen." ).that( actual ).isEqualTo( expected );
		}
		@Test
		void test15(@TempDir Path folder) throws FileNotFoundException {
			// create file
			Path path = folder.resolve( "foo.txt" );
			// add initial input file content
			try (var write = new PrintWriter( path.toFile() )) {
				String[] board = {
						"....k...",
						"........",
						"....n.b.",
						"....pr..",
						"..rpKp..",
						"....b...",
						"....qn..",
				        "........"
				};
				for (var s : board) {
					write.println( s );
				}
			}
			char expected = 'n';
			char actual   = Chess.go( path.toFile() );
			Truth.assertWithMessage( "Black king is in check by knight." ).that( actual ).isEqualTo( expected );
		}
	}
}
