package com.matttax.foodordering.domain.model

@JvmInline
value class CategoryDomainModel(
	val name: String
)

typealias Categories = List<CategoryDomainModel>
