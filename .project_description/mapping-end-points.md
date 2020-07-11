#Mapping requests and end points list.
Means GET - if not specified.

##Home
##Contacts
###/{root}/contacts
####/how_to_pay
####/delivery
####/contact

##Catalog.
###/{root}/catalog
####/all
####/{prod_id}/details

##Authentication.
###/{root}/login
###/{root}/authenticateTheUser (POST)

##Registration.
###/{root}/register
####/showForm
####/process (POST)

##User profile.
###/{root}/profile

####/form
######/show
#####/change/password/showForm
######/process/change/password (POST)
######/process/update/deliveryAddress (POST)
######/process/update/first_name (POST)
######/process/update/last_name (POST)

####/cart
#####/clear
#####/add/{prod_id}/prod_id
#####/delete/{prod_id}/prod_id
#####/update/{prod_id}/prod_id/{quantity}/quantity

####/order
#####/all
#####/proceedToCheckout
#####/rollBack
#####/create
#####/show/{order_id}/order_id
#####/edit/{order_id}/order_id
#####/delete/{order_id}/order_id
#####/cancel/{order_id}/order_id
#####/process/update/orderStatus (POST)
#####/process/update/delivery (POST)

##Admin section.
###/{root}/admin

####/product
#####/all
#####/create
#####/edit/{prod_id}/prod_id
#####/delete/{prod_id}/prod_id
#####/process/edit (POST)

####/category
#####/all
#####/create
#####/edit/{cat_id}/cat_id
#####/delete/{cat_id}/cat_id

####/user
#####/all
#####/reset/password/{user_id}/user_id
#####/edit/{user_id}/user_id
#####/{user_id}/user_id/add/{role_id}/role_id
#####/{user_id}/user_id/remove/{role_id}/role_id
#####/delete/{user_id}/user_id

####/user_role
#####/all
#####/create
#####/show/{role_id}/role_id
#####/edit/{role_id}/role_id
#####/delete/{role_id}/role_id

####/address
#####/all
#####/create
#####/show/{addr_id}/addr_id
#####/edit/{addr_id}/addr_id
#####/delete/{addr_id}/addr_id

####/order_status
#####/all
#####/create
#####/show/{ord_st_id}/ord_st_id
#####/edit/{ord_st_id}/ord_st_id
#####/delete/{ord_st_id}/ord_st_id

####/order
#####/all
#####/show/{order_id}/order_id
#####/edit/{order_id}/order_id
#####/delete/{order_id}/order_id
#####/cancel/{order_id}/order_id

