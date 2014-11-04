/*
 */
package com.copex.common;

import java.awt.Color;
import java.awt.Font;

import org.springframework.core.io.ClassPathResource;

import com.octo.captcha.component.image.backgroundgenerator.BackgroundGenerator;
import com.octo.captcha.component.image.backgroundgenerator.FileReaderRandomBackgroundGenerator;
import com.octo.captcha.component.image.color.RandomListColorGenerator;
import com.octo.captcha.component.image.fontgenerator.FontGenerator;
import com.octo.captcha.component.image.fontgenerator.RandomFontGenerator;
import com.octo.captcha.component.image.textpaster.DecoratedRandomTextPaster;
import com.octo.captcha.component.image.textpaster.TextPaster;
import com.octo.captcha.component.image.textpaster.textdecorator.TextDecorator;
import com.octo.captcha.component.image.wordtoimage.ComposedWordToImage;
import com.octo.captcha.component.word.wordgenerator.RandomWordGenerator;
import com.octo.captcha.engine.image.ListImageCaptchaEngine;
import com.octo.captcha.image.gimpy.GimpyFactory;

/**
 * Captcha Engine
 * 
 */
public class CaptchaEngine extends ListImageCaptchaEngine {

	private static final int IMAGE_WIDTH = 80;
	private static final int IMAGE_HEIGHT = 28;
	private static final int MIN_FONT_SIZE = 12;
	private static final int MAX_FONT_SIZE = 16;
	private static final int MIN_WORD_LENGTH = 4;
	private static final int MAX_WORD_LENGTH = 4;
	private static final String CHAR_STRING = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
	private static final String BACKGROUND_IMAGE_PATH = "/com/greenland/common/captcha/";
	private static final Font[] FONTS = new Font[] {
			new Font("nyala", Font.BOLD, MAX_FONT_SIZE),
			new Font("Arial", Font.BOLD, MAX_FONT_SIZE),
			new Font("nyala", Font.BOLD, MAX_FONT_SIZE),
			new Font("Bell", Font.BOLD, MAX_FONT_SIZE),
			new Font("Bell MT", Font.BOLD, MAX_FONT_SIZE),
			new Font("Impact", Font.BOLD, MAX_FONT_SIZE) };

	private static final Color[] COLORS = new Color[] {
			new Color(90, 90, 90), new Color(90, 70, 70),
			new Color(70, 90, 90), new Color(70, 70, 90),
			new Color(90, 90, 70), new Color(70, 90, 70) };

	@Override
	protected void buildInitialFactories() {
		FontGenerator fontGenerator = new RandomFontGenerator(MIN_FONT_SIZE,
				MAX_FONT_SIZE, FONTS);
		BackgroundGenerator backgroundGenerator = new FileReaderRandomBackgroundGenerator(
				IMAGE_WIDTH, IMAGE_HEIGHT, new ClassPathResource(
						BACKGROUND_IMAGE_PATH).getPath());
		TextPaster textPaster = new DecoratedRandomTextPaster(MIN_WORD_LENGTH,
				MAX_WORD_LENGTH, new RandomListColorGenerator(COLORS),
				new TextDecorator[] {});
		addFactory(new GimpyFactory(new RandomWordGenerator(CHAR_STRING),
				new ComposedWordToImage(fontGenerator, backgroundGenerator,
						textPaster)));
	}

}