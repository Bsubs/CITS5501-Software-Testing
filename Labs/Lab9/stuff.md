# Question 1

1. I would not use formal methods to test this application. The candy crush app is avaliable on web, IOS and Android. The bulk of testing will be divided into two parts:
    - Testing to ensure that the game is free of bugs, unit testing, integration testing and system testing
    - Load testing to ensure the servers can handle the demand
    - Testing to ensure that the game was successfully ported to all three platforms
        - hardware testing to determine the minimum specifications required to run 
    - Speed to market is more important for the games industry

2. No, alot of machine learning techniques can be treated as black boxes where we do not know how the internals operate. Testing these will require significant expertise and time.
    - If the market does not demand formal assurance, it's not worth the effort
    - Formal methods cannot prove that the classifier/model produced by the machine learning method is good

3. This needs formal methods to prove that the financial security of the customers are protected. The cost of fully verifying systems is typically not worth the sum

4. Two different problems
    - The radiation machine 100% needs formal methods to prove that the dose it is delivering is exact. A small deviation could kill the patient or not properly treat the tumors
    - Too expensive to formall veryfy that a database is working as expected.