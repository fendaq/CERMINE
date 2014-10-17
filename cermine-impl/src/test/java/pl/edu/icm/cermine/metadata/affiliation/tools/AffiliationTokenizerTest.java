package pl.edu.icm.cermine.metadata.affiliation.tools;

import java.util.Arrays;
import java.util.List;
import static org.junit.Assert.assertEquals;
import org.junit.Test;
import pl.edu.icm.cermine.metadata.model.AffiliationLabel;
import pl.edu.icm.cermine.metadata.model.DocumentAffiliation;
import pl.edu.icm.cermine.metadata.tools.MetadataTools;
import pl.edu.icm.cermine.parsing.model.Token;

public class AffiliationTokenizerTest {
	
	private final static AffiliationTokenizer tokenizer = new AffiliationTokenizer();
	
	@Test
	public void testTokenizeAscii() {
		//              012345678901234567
		String input = "ko  pi_es123_@@123Kot";
		// "ko", "pi" ,"_", "es", "123", "_", "@", "@", "123"
		
		List<Token<AffiliationLabel>> expected = Arrays.asList(
				new Token<AffiliationLabel>("ko", 0, 2),
				new Token<AffiliationLabel>("pi", 4, 6),
				new Token<AffiliationLabel>("_", 6, 7),
				new Token<AffiliationLabel>("es", 7, 9),
				new Token<AffiliationLabel>("123", 9, 12),
				new Token<AffiliationLabel>("_", 12, 13),
				new Token<AffiliationLabel>("@", 13, 14),
				new Token<AffiliationLabel>("@", 14, 15),
				new Token<AffiliationLabel>("123", 15, 18),
				new Token<AffiliationLabel>("Kot", 18, 21)
				);
		
		List<Token<AffiliationLabel>> actual = tokenizer.tokenize(input);
		
		assertEquals(expected, actual);
	}
	
	@Test
	public void testTokenizeNonAscii() {
		String input = "śćdź óó";
		input = MetadataTools.cleanAndNormalize(input);
		//input = new AffiliationNormalizer().normalize(input);
		
		//          012345678901
		// input = "s'c'dz' o'o'"
		
		
		List<Token<AffiliationLabel>> expected = Arrays.asList(
				new Token<AffiliationLabel>("scdz", 0, 7),
				new Token<AffiliationLabel>("oo", 8, 12)
				);
		
		List<Token<AffiliationLabel>> actual = tokenizer.tokenize(input);
		
		assertEquals(expected, actual);
	}

	@Test
	public void testTokenizeWithDocumentAffiliation() {
		// 0123456789012345
		// Co'z' ro123bic'?
		String text = "Cóż ro123bić?";
		List<Token<AffiliationLabel>> expected = Arrays.asList(
				new Token<AffiliationLabel>("Coz", 0, 5),
				new Token<AffiliationLabel>("ro", 6, 8),
				new Token<AffiliationLabel>("123", 8, 11),
				new Token<AffiliationLabel>("bic", 11, 15),
				new Token<AffiliationLabel>("?", 15, 16)
				);
				
		DocumentAffiliation instance = new DocumentAffiliation(text);
		
		List<Token<AffiliationLabel>> actual = tokenizer.tokenize(instance.getRawText());
		
        System.out.println(actual);
        
		assertEquals(expected, actual);
	}

}