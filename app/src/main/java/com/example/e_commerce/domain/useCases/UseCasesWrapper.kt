package com.example.e_commerce.domain.useCases

data class UseCasesWrapper(
    val signInUseCase: SignInUseCase,
    val signUpUseCase: SignUpUseCase,
    val addProductImageUseCase: AddProductImageUseCase,
    val addNewProductUseCase: AddNewProductUseCase,
    val getAllProductsUseCase: GetAllProductsUseCase,
    val updateUserData: UpdateUserDataUseCase,
    val getCurrentUserUseCase: GetCurrentUserUseCase,
    val uploadUserProfileImageUseCase: UploadUserProfileImageUseCase,
    val getSingleProductUseCase: GetSingleProductUseCase,
    val uploadUserReview: UploadUserReviewUseCase,
    val addOrderToCart: AddOrderToCartUseCase,
    val deleteOrderFromCart: DeleteOrderFromCartUseCase,
    val deleteAllOrdersFromCartUserCase: DeleteAllOrdersFromCartUserCase,
    val submitUserOrders: SubmitUserOrdersUseCase,
    val getAllOrdersInCartUseCase: GetAllOrdersInCartUseCase,
)
