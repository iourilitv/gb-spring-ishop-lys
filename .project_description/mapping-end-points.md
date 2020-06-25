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
######/showForm
######/process (POST)
#####/edit
#####/change/password

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
//#####/process/edit/{order_id}/order_id (POST)

#####/delete/{order_id}/order_id
#####/cancel/{order_id}/order_id

#####/process/update/orderStatus (POST)
#####/process/update/delivery (POST)
#####/process/update/orderItems (POST)

##Admin section.
###/{root}/admin

####/user
#####/all
#####/create
#####/show/{user_id}/user_id
#####/edit/{user_id}/user_id
#####/delete/{user_id}/user_id

####/product
#####/all
#####/create
#####/show/{prod_id}/prod_id
#####/edit/{prod_id}/prod_id
#####/delete/{prod_id}/prod_id

####/category
#####/all
#####/create
#####/show/{cat_id}/cat_id
#####/edit/{cat_id}/cat_id
#####/delete/{cat_id}/cat_id

####/role
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

####/order
#####/all
#####/show/{order_id}/order_id
#####/edit/{order_id}/order_id
#####/delete/{order_id}/order_id
#####/cancel/{order_id}/order_id

####/order_status
#####/all
#####/create
#####/show/{ord_st_id}/ord_st_id
#####/edit/{ord_st_id}/ord_st_id
#####/delete/{ord_st_id}/ord_st_id

