package com.fguery.substitution;

import java.util.HashMap;

public class Substitution {

    public boolean canReachEnd(String start, String end, String[] substitutions) throws Exception{

        HashMap<String, String> mappedSubstitutions = mapSubstitutions(substitutions);

        SubstituteOnce singleSubstitutor = new SubstituteOnce(mappedSubstitutions);

        String lastOutput;
        String currentOutput = start;

        do {
            lastOutput = currentOutput;
            currentOutput = singleSubstitutor.substituteOnce(lastOutput);
        } while (!currentOutput.equals(lastOutput));

        return currentOutput.equals(end);
    }

    private HashMap<String, String> mapSubstitutions(String[] substitutions) throws Exception{
        HashMap<String, String> mappedSubstitutions = new HashMap<>();
        for (String substitution:substitutions) {
            String[] startAndResult = substitution.split("->");
            if (startAndResult[0].length() == 0) {
                throw new Exception("Invalid substitution (" + substitution + ")");
            }
            if (startAndResult.length > 1) {
                mappedSubstitutions.put(startAndResult[0], startAndResult[1]);
            } else {
                mappedSubstitutions.put(startAndResult[0], "");
            }
        }
        return mappedSubstitutions;
    }

    class SubstituteOnce {
        HashMap<String, String> substitutions;
        String currentToken;
        int next;
        SubstituteOnce(HashMap<String, String> substitutions) {
            this.substitutions = substitutions;
        }

        private String findValidToken(String input) {
            currentToken += input.substring(next, next+1);
            next++;
            if (!substitutions.containsKey(currentToken)) {
                if (next >= input.length()) {
                    return "";
                }
                return findValidToken(input);
            } else {
                return currentToken;
            }
        }

        /**
         * This class is responsible to perform one substitution, i.e. it'll only substitute
         * instances found on the current string, not on its results.
         */
        String substituteOnce(String input) {
            String output = "";
            currentToken = "";
            next = 0;
            while (next < input.length()) {
                String foundToken = findValidToken(input);
                if (foundToken.equals("")) {
                    // We were not able to find a token, we ignore the 1st character
                    next = input.length() - currentToken.length() + 1;
                    output += currentToken.substring(0, 1);
                } else {
                    output += substitutions.get(foundToken);
                }
                currentToken = "";
            }
            return output;
        }
    }
}
