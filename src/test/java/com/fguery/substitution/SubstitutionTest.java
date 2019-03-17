package com.fguery.substitution;

import org.junit.Ignore;
import org.junit.Test;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;

public class SubstitutionTest {

    private Substitution substitution = new Substitution();

    @Test
    public void canReachEnd_returns_true_when_end_is_equals_to_result() throws Exception{
        // Given a substitution list that would reach the end
        String start = "A";
        String end = "I";
        String[] substitutions = {"A->BC", "BC->D", "D->EF", "E->G", "F->H", "GH->I"};

        // When
        boolean result = substitution.canReachEnd(start, end, substitutions);

        // Then
        assertTrue(result);
    }

    @Test
    public void canReachEnd_returns_false_when_there_is_no_more_substitutions() throws Exception {
        // Given a substitution list that would stop because intermediate result doens't have any available substitution
        String start = "A";
        String end = "I";
        String[] substitutions = {"A->BC", "BC->D", "D->EF", "E->G", "F->H", "GH->J"};

        // When
        boolean result = substitution.canReachEnd(start, end, substitutions);

        // Then
        assertFalse(result);
    }

    @Test
    public void canReadEnd_returns_false_when_end_result_contains_too_many_letters() throws Exception {
        // Given a substitution list that would reach end with an additional letter
        String start = "A";
        String end = "I";
        String[] substitutions = {"A->BC", "BC->D", "D->EF", "E->G", "F->H", "GH->JI"};

        // When
        boolean result = substitution.canReachEnd(start, end, substitutions);

        // Then
        assertFalse(result);
    }

    @Test
    public void canReachEnd_supports_blank_replacements() throws Exception {
        String start = "AB";
        String end = "B";
        String[] substitutions = {"A->"};

        // When
        boolean result = substitution.canReachEnd(start, end, substitutions);

        // Then
        assertTrue(result);
    }

    @Test
    public void canReachEnd_replaces_all_instances() throws Exception {
        String start = "AAB";
        String end = "BBB";
        String[] substitutions = {"A->B"};

        // When
        boolean result = substitution.canReachEnd(start, end, substitutions);

        // Then
        assertTrue(result);
    }

    @Test
    public void canReachEnd_tolerates_not_substituted_characters() throws Exception {
        String start = "ABA";
        String end = "B";
        String[] substitutions = {"A->"};

        // When
        boolean result = substitution.canReachEnd(start, end, substitutions);

        // Then
        assertTrue(result);
    }

    @Test(expected = Exception.class)
    public void canReachEnd_throws_exception_on_invalid_substitution() throws Exception {
        String start = "A";
        String end = "";
        String[] substitutions = {"->AB"};

        // When
        boolean result = substitution.canReachEnd(start, end, substitutions);

        // Then
        assertTrue(result);
    }

    @Test
    public void canReachEnd_supports_blank_input() throws Exception {
        String start = "";
        String end = "";
        String[] substitutions = {"AB->"};

        // When
        boolean result = substitution.canReachEnd(start, end, substitutions);

        // Then
        assertTrue(result);
    }

    @Test
    public void canReachEnd_supports_blank_result() throws Exception {
        String start = "AB";
        String end = "";
        String[] substitutions = {"AB->"};

        // When
        boolean result = substitution.canReachEnd(start, end, substitutions);

        // Then
        assertTrue(result);
    }
}
