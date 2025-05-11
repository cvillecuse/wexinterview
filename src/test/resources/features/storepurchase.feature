Feature: Store Purchase API
  I want to test the store purchase API

  Scenario: Create and retrieve a Store Purchase using Store Purchase API
    When I create a new store purchase with the payload
    """
    {
	    "description": "This is a test",
	    "purchaseAmount": 20.005,
	    "transactionDate": "2025-05-10 04:48:01"
	}
	"""
	Then I receive the response
	"""
	{
		"description": "This is a test",
	    "purchaseAmount": 20.01,
	    "transactionDate": "2025-05-10T04:48:01"
	}
	"""
	When I retrieve a store purchase with the Id 1 and the country currency "Canada-Dollar"
	Then I receive the response
	"""
	{
		"description": "This is a test",
	    "purchaseAmount": 20.01,
	    "transactionDate": "2025-05-10T04:48:01",
	    "exchangeRate": 1.4,
	    "countryCurrencyDesc": "Canada-Dollar",
	    "exchangePurchaseAmount": 28.01
	}
	"""	

    