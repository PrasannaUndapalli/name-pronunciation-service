package com.wf.nps.controller;

import java.util.Scanner;
import java.util.concurrent.ExecutionException;

import com.microsoft.cognitiveservices.speech.CancellationDetails;
import com.microsoft.cognitiveservices.speech.CancellationReason;
import com.microsoft.cognitiveservices.speech.OutputFormat;
import com.microsoft.cognitiveservices.speech.PronunciationAssessmentConfig;
import com.microsoft.cognitiveservices.speech.PronunciationAssessmentGradingSystem;
import com.microsoft.cognitiveservices.speech.PronunciationAssessmentGranularity;
import com.microsoft.cognitiveservices.speech.PronunciationAssessmentResult;
import com.microsoft.cognitiveservices.speech.PropertyId;
import com.microsoft.cognitiveservices.speech.ResultReason;
import com.microsoft.cognitiveservices.speech.SpeechConfig;
import com.microsoft.cognitiveservices.speech.SpeechRecognitionResult;
import com.microsoft.cognitiveservices.speech.SpeechRecognizer;
import com.microsoft.cognitiveservices.speech.SpeechSynthesisResult;
import com.microsoft.cognitiveservices.speech.SpeechSynthesizer;

public class SpeechSynthesis {
	
	private static String YourSubscriptionKey = "1346fbaa8ea14dacb905138882f5a8a2";
	private static String YourServiceRegion = "eastus";

	public byte[] getTextToSpeech(String text) throws InterruptedException, ExecutionException {
		
		SpeechConfig speechConfig = SpeechConfig.fromSubscription(YourSubscriptionKey, YourServiceRegion);
		
		// speechConfig.setSpeechSynthesisVoiceName("en-US-JennyNeural");

		SpeechSynthesizer speechSynthesizer = new SpeechSynthesizer(speechConfig);

		SpeechSynthesisResult speechRecognitionResult = speechSynthesizer.SpeakTextAsync(text).get();
		
		return speechRecognitionResult.getAudioData();
//		if (speechRecognitionResult.getReason() == ResultReason.SynthesizingAudioCompleted) {
//			System.out.println("Speech synthesized to speaker for text [" + text + "]");
//		} 
			
//		if (speechRecognitionResult.getReason() == ResultReason.Canceled) {
//			SpeechSynthesisCancellationDetails cancellation = SpeechSynthesisCancellationDetails
//					.fromResult(speechRecognitionResult);
//			System.out.println("CANCELED: Reason=" + cancellation.getReason());
//
//			if (cancellation.getReason() == CancellationReason.Error) {
//				System.out.println("CANCELED: ErrorCode=" + cancellation.getErrorCode());
//				System.out.println("CANCELED: ErrorDetails=" + cancellation.getErrorDetails());
//				System.out.println("CANCELED: Did you set the speech resource key and region values?");
//			}
//		}
//		return null;
	}
	
//	public static void main(String[] args) throws InterruptedException, ExecutionException {
//		System.out.println(getTextToPhonemes("Geetha"));
//		//recognitionWithMicrophoneAsyncAndDetailedRecognitionResults();
//	}
	
	
	// Speech recognition from microphone, showing detailed recognition results including word-level timing
    public static void recognitionWithMicrophoneAsyncAndDetailedRecognitionResults() throws InterruptedException, ExecutionException
    {
        // <recognitionWithMicrophoneAndDetailedRecognitionResults>
        // Creates an instance of a speech config with specified
        // subscription key and service region. Replace with your own subscription key
        // and service region (e.g., "westus").
        // The default language is "en-us".
        SpeechConfig config = SpeechConfig.fromSubscription(YourSubscriptionKey, YourServiceRegion);

        // Ask for detailed recognition result
        config.setOutputFormat(OutputFormat.Detailed);

        // If you also want word-level timing in the detailed recognition results, set the following.
        // Note that if you set the following, you can omit the previous line
        //   "config.setOutputFormat(OutputFormat.Detailed)",
        // since word-level timing implies detailed recognition results.
        config.requestWordLevelTimestamps();

        // Creates a speech recognizer using microphone as audio input.
        SpeechRecognizer recognizer = new SpeechRecognizer(config);
        {
            
        	
        	// Starts recognizing.
            System.out.println("Say something...");

            // Starts recognition. It returns when the first utterance has been recognized.
            SpeechRecognitionResult result = recognizer.recognizeOnceAsync().get();

            // Checks result.
            if (result.getReason() == ResultReason.RecognizedSpeech) {
                System.out.println("RECOGNIZED: Text = " + result.getText());
                
                PronunciationAssessmentConfig pronunciationConfig = new PronunciationAssessmentConfig(result.getText(),
        				PronunciationAssessmentGradingSystem.HundredMark, PronunciationAssessmentGranularity.Phoneme, true);
        		pronunciationConfig.applyTo(recognizer);

                // Time units are in hundreds of nanoseconds (HNS), where 10000 HNS equals 1 millisecond
                System.out.println("Offset: " + result.getOffset());
                System.out.println("Duration: " + result.getDuration());

                // Now get the detailed recognition results as a JSON string
                String jsonText = result.getProperties().getProperty(PropertyId.SpeechServiceResponse_JsonResult);
                System.out.println(jsonText);
                // Convert the JSON string to a JSON object
//                JsonReader jsonReader = Json.createReader(new StringReader(jsonText));
//
//                // Extract the "NBest" array of recognition results from the JSON.
//                // Note that the first cell in the NBest array corresponds to the recognition results
//                // (NOT the cell with the highest confidence number!)
//                JsonArray nbestArray = jsonReader.readObject().getJsonArray("NBest");
//
//                for (int i = 0; i < nbestArray.size(); i++) {
//                    JsonObject nbestItem = nbestArray.getJsonObject(i);
//                    System.out.println("\tConfidence: " + nbestItem.getJsonNumber("Confidence"));
//                    System.out.println("\tLexical: " + nbestItem.getString("Lexical"));
//                    // ITN stands for Inverse Text Normalization
//                    System.out.println("\tITN: " + nbestItem.getString("ITN"));
//                    System.out.println("\tMaskedITN: " + nbestItem.getString("MaskedITN"));
//                    System.out.println("\tDisplay: " + nbestItem.getString("Display"));
//
//                    // Word-level timing
//                    JsonArray wordsArray = nbestItem.getJsonArray("Words");
//                    System.out.println("\t\tWord | Offset | Duration");
//                    for (int j = 0; j < wordsArray.size(); j++) {
//                        JsonObject wordItem = wordsArray.getJsonObject(j);
//                        System.out.println("\t\t" + wordItem.getString("Word") + " " + wordItem.getJsonNumber("Offset") + " " + wordItem.getJsonNumber("Duration"));
//                    }
//                }
//
//                jsonReader.close();
            }
            else if (result.getReason() == ResultReason.NoMatch) {
                System.out.println("NOMATCH: Speech could not be recognized.");
            }
            else if (result.getReason() == ResultReason.Canceled) {
                CancellationDetails cancellation = CancellationDetails.fromResult(result);
                System.out.println("CANCELED: Reason=" + cancellation.getReason());

                if (cancellation.getReason() == CancellationReason.Error) {
                    System.out.println("CANCELED: ErrorCode=" + cancellation.getErrorCode());
                    System.out.println("CANCELED: ErrorDetails=" + cancellation.getErrorDetails());
                    System.out.println("CANCELED: Did you update the subscription info?");
                }
            }

            result.close();
        }

        config.close();
        recognizer.close();
        // </recognitionWithMicrophoneAndDetailedRecognitionResults>
    }
    
    public static String getTextToPhonemes(String text) throws InterruptedException, ExecutionException {
		SpeechConfig speechConfig = SpeechConfig.fromSubscription(YourSubscriptionKey, YourServiceRegion);
		SpeechRecognizer recognizer = new SpeechRecognizer(speechConfig, "en-US");
		PronunciationAssessmentConfig pronunciationConfig = new PronunciationAssessmentConfig(text,
				PronunciationAssessmentGradingSystem.HundredMark, PronunciationAssessmentGranularity.Phoneme, true);
		pronunciationConfig.applyTo(recognizer);
		SpeechRecognitionResult result = recognizer.recognizeOnceAsync().get();
		PronunciationAssessmentResult pronunciationResult = PronunciationAssessmentResult.fromResult(result);
		
		String jsonText = result.getProperties().getProperty(PropertyId.SpeechServiceResponse_JsonResult);

		return jsonText;
	}
    
    public static void pronunciationAssessmentWithMicrophoneAsync() throws ExecutionException, InterruptedException {
        // Creates an instance of a speech config with specified subscription key and service region.
        // Replace with your own subscription key and service region (e.g., "westus").
        SpeechConfig config = SpeechConfig.fromSubscription(YourSubscriptionKey, YourServiceRegion);

        // Replace the language with your language in BCP-47 format, e.g., en-US.
        String lang = "en-US";

        // The pronunciation assessment service has a longer default end silence timeout (5 seconds) than normal STT
        // as the pronunciation assessment is widely used in education scenario where kids have longer break in reading.
        // You can adjust the end silence timeout based on your real scenario.
        //config.setProperty(PropertyId.SpeechServiceConnection_EndSilenceTimeoutMs, "3000");

        String referenceText = "";
        // create pronunciation assessment config, set grading system, granularity and if enable miscue based on your requirement.
        PronunciationAssessmentConfig pronunciationConfig = new PronunciationAssessmentConfig(referenceText,
            PronunciationAssessmentGradingSystem.HundredMark, PronunciationAssessmentGranularity.Phoneme, true);

        while (true)
        {
            // Creates a speech recognizer for the specified language, using microphone as audio input.
            SpeechRecognizer recognizer = new SpeechRecognizer(config, lang);
            {
                // Receives reference text from console input.
                System.out.println("Enter reference text you want to assess, or enter empty text to exit.");
                System.out.print("> ");
                referenceText = new Scanner(System.in).nextLine();
                if (referenceText.isEmpty())
                {
                    break;
                }

                pronunciationConfig.setReferenceText(referenceText);

                // Starts recognizing.
                System.out.println("Read out \"" + referenceText + "\" for pronunciation assessment ...");

                pronunciationConfig.applyTo(recognizer);

                // Starts speech recognition, and returns after a single utterance is recognized.
                // For long-running multi-utterance recognition, use StartContinuousRecognitionAsync() instead.
                SpeechRecognitionResult result = recognizer.recognizeOnceAsync().get();
                
                String jsonText = result.getProperties().getProperty(PropertyId.SpeechServiceResponse_JsonResult);
                System.out.println(jsonText);

                // Checks result.
                if (result.getReason() == ResultReason.RecognizedSpeech) {
                    System.out.println("RECOGNIZED: Text=" + result.getText());
                    System.out.println("  PRONUNCIATION ASSESSMENT RESULTS:");

                    PronunciationAssessmentResult pronunciationResult = PronunciationAssessmentResult.fromResult(result);
                    System.out.println(
                        String.format(
                            "    Accuracy score: %f, Pronunciation score: %f, Completeness score : %f, FluencyScore: %f",
                            pronunciationResult.getAccuracyScore(), pronunciationResult.getPronunciationScore(),
                            pronunciationResult.getCompletenessScore(), pronunciationResult.getFluencyScore()));
                }
                else if (result.getReason() == ResultReason.NoMatch) {
                    System.out.println("NOMATCH: Speech could not be recognized.");
                }
                else if (result.getReason() == ResultReason.Canceled) {
                    CancellationDetails cancellation = CancellationDetails.fromResult(result);
                    System.out.println("CANCELED: Reason=" + cancellation.getReason());

                    if (cancellation.getReason() == CancellationReason.Error) {
                        System.out.println("CANCELED: ErrorCode=" + cancellation.getErrorCode());
                        System.out.println("CANCELED: ErrorDetails=" + cancellation.getErrorDetails());
                        System.out.println("CANCELED: Did you update the subscription info?");
                    }
                }

                result.close();
                recognizer.close();
            }
        }

        pronunciationConfig.close();
        config.close();
    }
}
