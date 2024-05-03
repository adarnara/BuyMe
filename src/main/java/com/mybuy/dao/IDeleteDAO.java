package com.mybuy.dao;

import com.mybuy.model.Delete;

public interface IDeleteDAO {
	boolean delete(Delete user);
	boolean deleteAuction(Delete auction);
	boolean deleteBid(Delete bid);
}
