package org.dasin.web.servlet;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.dasin.tools.dWebTools;

public class CaptchaServlet extends HttpServlet{
	private static final long serialVersionUID = 1L;
	Random random = new Random(System.currentTimeMillis());

	Color getRandColor(){
		return new Color(128 + random.nextInt(128), 128 + random.nextInt(128), 128 + random.nextInt(128));
	}

	String generateCaptcha(int length){
		StringBuilder result = new StringBuilder();

		for(int i = 0;i < length;i++){
			result.append(random.nextInt(10));
		}

		return result.toString();
	}

	BufferedImage getImage(String captcha){
		BufferedImage image = new BufferedImage(captcha.length()*13 + 10, 20, BufferedImage.TYPE_INT_RGB);
		Graphics g = image.getGraphics();

		g.setFont(new Font("Arial Black", Font.PLAIN, 18));

		for(int i = 0;i < captcha.length();i++){
			g.setColor(getRandColor());
			g.drawString(captcha.substring(i, i + 1), 13*i + 5, 16);
		}

		g.dispose();
		return image;
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doPost(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("image/jpeg");
		dWebTools.setResponseNoCache(response);

		int length = 4;
		try{
			if(request.getParameter("length") != null){
				length = Integer.parseInt(request.getParameter("length"));
			}
		}catch(Exception e){}

		String captcha = generateCaptcha(length);
		request.getSession().setAttribute("captcha", captcha);
		ImageIO.write(getImage(captcha), "JPEG", response.getOutputStream());
	}
}
