package com.real.fudousan.texture.dao;

import java.util.List;

import com.real.fudousan.texture.vo.Texture;

public interface TextureDAO {

	//텍스쳐 입력
	public int textureuproad(Texture texture);
	
	public List<Texture> selectAllTexture();
}
